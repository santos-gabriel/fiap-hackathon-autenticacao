package com.fiap.authenticacao.application.web.configuration;

import com.fiap.authenticacao.domain.model.Usuario;
import com.fiap.authenticacao.domain.model.valueObject.Email;
import com.fiap.authenticacao.domain.model.valueObject.UserName;
import com.fiap.authenticacao.domain.ports.in.IUsuarioUseCasePort;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class JwtRequestFilter  extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtAccess;
    @Autowired
    private IUsuarioUseCasePort usuarioUseCasePort;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;
        Claims claims = null;

        if (authorizationHeader != null) {
            jwt = authorizationHeader.substring(7);
            username = jwtAccess.extractUsername(jwt);
            claims = jwtAccess.extractAllClaims(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            String nome = claims.get("nome", String.class);
            String email = claims.get("email", String.class);
            Usuario usuario = Usuario.builder()
                    .id(UUID.fromString(claims.get("id", String.class)))
                    .nome(Objects.nonNull(nome) ? new UserName(nome) : null)
                    .matricula(claims.get("matricula", String.class))
                    .email(Objects.nonNull(email) ? new Email(email) : null)
                    .build();
            if (jwtAccess.validateToken(jwt, usuario)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        usuario, null, null);
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }

        filterChain.doFilter(request, response);

    }
}

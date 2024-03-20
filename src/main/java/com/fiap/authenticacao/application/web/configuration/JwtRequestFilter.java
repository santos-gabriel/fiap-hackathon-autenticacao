package com.fiap.authenticacao.application.web.configuration;

import com.fiap.authenticacao.domain.model.Usuario;
import com.fiap.authenticacao.domain.model.valueObject.UserName;
import com.fiap.authenticacao.domain.ports.in.IUsuarioUseCasePort;
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
import java.util.Optional;

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

        if (authorizationHeader != null) {
            jwt = authorizationHeader.substring(7);
            username = jwtAccess.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<Usuario> usuario = this.usuarioUseCasePort.localizaPorNome(new UserName(username));
            if ((usuario.isPresent()) && (jwtAccess.validateToken(jwt, usuario.get()))) {
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

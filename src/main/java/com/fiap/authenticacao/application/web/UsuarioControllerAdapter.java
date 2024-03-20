package com.fiap.authenticacao.application.web;

import com.fiap.authenticacao.application.web.configuration.JwtService;
import com.fiap.authenticacao.application.web.request.UsuarioRequest;
import com.fiap.authenticacao.application.web.response.JwtResponse;
import com.fiap.authenticacao.domain.model.Usuario;
import com.fiap.authenticacao.domain.model.valueObject.Senha;
import com.fiap.authenticacao.domain.model.valueObject.UserName;
import com.fiap.authenticacao.domain.ports.in.IUsuarioUseCasePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/authenticate")
@RequiredArgsConstructor
public class UsuarioControllerAdapter {

    private final IUsuarioUseCasePort usuarioUseCasePort;
    @Autowired
    private JwtService jwtService;

    @PostMapping(value = {"/", ""}, produces = "application/json")
    public ResponseEntity<?> autenticacao(@RequestBody @Valid UsuarioRequest usuario) {
        Usuario user = Usuario.builder()
                .nome(new UserName(usuario.getNome()))
                .senha(new Senha(usuario.getSenha()))
                .build();
        Optional<Usuario> usuarioLogin = usuarioUseCasePort.realizaLogin(user);
        if (usuarioLogin.isPresent()) {
            return ResponseEntity.ok(new JwtResponse(jwtService.generateToken(usuarioLogin.get())));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuário ou senha inválidos");
    }
}

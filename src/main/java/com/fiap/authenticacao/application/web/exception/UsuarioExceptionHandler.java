package com.fiap.authenticacao.application.web.exception;

import com.fiap.authenticacao.application.web.UsuarioControllerAdapter;
import com.fiap.authenticacao.domain.exception.LoginInvalidoException;
import com.fiap.authenticacao.domain.exception.SenhaFracaException;
import com.fiap.authenticacao.domain.exception.UsuarioExistenteException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice(assignableTypes = UsuarioControllerAdapter.class)
public class UsuarioExceptionHandler {
    @ExceptionHandler(SenhaFracaException.class)
    public ResponseEntity<?> senhaFraca(SenhaFracaException e, HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Senha fraca", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
    @ExceptionHandler(LoginInvalidoException.class)
    public ResponseEntity<?> loginInvalido(LoginInvalidoException e, HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Usuário ou senha inválidos", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
    @ExceptionHandler(UsuarioExistenteException.class)
    public ResponseEntity<?> usuarioExistente(UsuarioExistenteException e, HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(), "Nome de usuário não disponível", e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}

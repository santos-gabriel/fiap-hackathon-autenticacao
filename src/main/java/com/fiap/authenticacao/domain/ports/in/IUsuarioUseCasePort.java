package com.fiap.authenticacao.domain.ports.in;

import com.fiap.authenticacao.domain.exception.LoginInvalidoException;
import com.fiap.authenticacao.domain.exception.SenhaFracaException;
import com.fiap.authenticacao.domain.exception.UsuarioExistenteException;
import com.fiap.authenticacao.domain.model.Usuario;
import com.fiap.authenticacao.domain.model.valueObject.Senha;
import com.fiap.authenticacao.domain.model.valueObject.UserName;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUsuarioUseCasePort {
    Optional<Usuario> realizaLogin(Usuario usuario) throws LoginInvalidoException, SenhaFracaException;
    Optional<Usuario> localizaPorNome(UserName nome) throws LoginInvalidoException;
    Usuario cadastra(Usuario usuario) throws LoginInvalidoException, SenhaFracaException, UsuarioExistenteException;
    Usuario atualizar(Usuario usuario, UUID id) throws LoginInvalidoException, SenhaFracaException, UsuarioExistenteException;
    void excluir(UUID id) throws LoginInvalidoException, SenhaFracaException, UsuarioExistenteException;
    List<Usuario> obterTodos();
}

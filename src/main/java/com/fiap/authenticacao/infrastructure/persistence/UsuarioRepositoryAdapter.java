package com.fiap.authenticacao.infrastructure.persistence;

import com.fiap.authenticacao.domain.exception.LoginInvalidoException;
import com.fiap.authenticacao.domain.exception.SenhaFracaException;
import com.fiap.authenticacao.domain.exception.UsuarioExistenteException;
import com.fiap.authenticacao.domain.model.Usuario;
import com.fiap.authenticacao.domain.model.valueObject.Senha;
import com.fiap.authenticacao.domain.model.valueObject.UserName;
import com.fiap.authenticacao.domain.ports.out.IUsuarioRepositoryPort;
import com.fiap.authenticacao.infrastructure.Encrypt;
import com.fiap.authenticacao.infrastructure.persistence.entity.UsuarioEntity;
import com.fiap.authenticacao.infrastructure.persistence.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements IUsuarioRepositoryPort {
    private final UsuarioRepository repository;

    @Override
    public Optional<Usuario> realizaLogin(UserName nome, Senha senha) throws LoginInvalidoException, SenhaFracaException {
        if (Objects.isNull(nome)) {
            throw new LoginInvalidoException();
        }
        if (Objects.isNull(senha)) {
            throw new SenhaFracaException();
        }

        Optional<UsuarioEntity> usuariodb = repository.findByNome(nome);
        if (usuariodb.isEmpty()) {
            throw new LoginInvalidoException();
        }

        if (!Encrypt.verificaHash(senha.getValue(), usuariodb.get().getSenha().getValue())) {
            throw new LoginInvalidoException();
        }

        return Optional.of(new UsuarioEntity().to(usuariodb.get()));
    }

    @Override
    public Optional<Usuario> realizaLogin(String matricula, Senha senha) throws LoginInvalidoException, SenhaFracaException {
        if (Objects.isNull(matricula)) {
            throw new LoginInvalidoException();
        }
        if (Objects.isNull(senha)) {
            throw new SenhaFracaException();
        }

        Optional<UsuarioEntity> usuariodb = repository.findByMatricula(matricula);
        if (usuariodb.isEmpty()) {
            throw new LoginInvalidoException();
        }

        if (!Encrypt.verificaHash(senha.getValue(), usuariodb.get().getSenha().getValue())) {
            throw new LoginInvalidoException();
        }

        return Optional.of(new UsuarioEntity().to(usuariodb.get()));
    }

    @Override
    public Optional<Usuario> localizaPorNome(UserName nome) throws LoginInvalidoException {
        Optional<UsuarioEntity> usuariodb = repository.findByNome(nome);
        return usuariodb.isPresent() ? Optional.of(new UsuarioEntity().to(usuariodb.get())) : Optional.empty();
    }

    @Override
    public Optional<Usuario> localizaPorMatricula(String matricula) throws LoginInvalidoException {
        Optional<UsuarioEntity> usuariodb = repository.findByMatricula(matricula);
        return usuariodb.isPresent() ? Optional.of(new UsuarioEntity().to(usuariodb.get())) : Optional.empty();
    }

    @Override
    public Usuario cadastra(Usuario usuario) throws LoginInvalidoException, SenhaFracaException {
        if (Objects.isNull(usuario)) {
            throw new LoginInvalidoException();
        }
        if (Objects.isNull(usuario.getNome())) {
            throw new LoginInvalidoException();
        }
        if (Objects.isNull(usuario.getSenha())) {
            throw new SenhaFracaException();
        }
        usuario.atualizaSenha(new Senha(Encrypt.gerarHash(usuario.getSenha().getValue())));
        UsuarioEntity usersaved = repository.save(new UsuarioEntity().from(usuario));
        return new UsuarioEntity().to(usersaved);
    }

    @Override
    public Usuario atualizar(Usuario usuario, UUID id) throws LoginInvalidoException, SenhaFracaException, UsuarioExistenteException {
        if (Objects.isNull(usuario)) {
            throw new LoginInvalidoException();
        }
        if (Objects.isNull(usuario.getNome())) {
            throw new LoginInvalidoException();
        }
        if (Objects.isNull(usuario.getSenha())) {
            throw new SenhaFracaException();
        }
        usuario.atualizaSenha(new Senha(Encrypt.gerarHash(usuario.getSenha().getValue())));
        Optional<UsuarioEntity> usuariodb = repository.findById(id);
        if (usuariodb.isEmpty()) {
            throw new LoginInvalidoException();
        }
        usuariodb.get().atualizaDados(usuario);
        return new UsuarioEntity().to(repository.save(usuariodb.get()));
    }

    @Override
    public void excluir(UUID id) throws LoginInvalidoException, SenhaFracaException, UsuarioExistenteException {
        repository.deleteById(id);
    }

    @Override
    public List<Usuario> obterTodos() {
        return repository.findAll().stream().map(e -> new UsuarioEntity().to(e)).collect(Collectors.toList());
    }
}

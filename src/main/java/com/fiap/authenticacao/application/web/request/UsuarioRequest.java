package com.fiap.authenticacao.application.web.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fiap.authenticacao.domain.model.Usuario;
import com.fiap.authenticacao.domain.model.valueObject.Email;
import com.fiap.authenticacao.domain.model.valueObject.Senha;
import com.fiap.authenticacao.domain.model.valueObject.UserName;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UsuarioRequest {
    private String nome;
    private String matricula;
    private String senha;
    private String email;

    public Usuario to(UsuarioRequest usuario) {
        return Usuario.builder()
                .nome(Objects.nonNull(usuario.getNome()) ? new UserName(usuario.getNome()) : null)
                .matricula(usuario.getMatricula())
                .senha(Objects.nonNull(usuario.getSenha()) ? new Senha(usuario.getSenha()) : null)
                .email(Objects.nonNull(usuario.getEmail()) ? new Email(usuario.getEmail()) : null)
                .build();
    }
}

package com.fiap.authenticacao.domain.model;

import com.fiap.authenticacao.domain.model.valueObject.Email;
import com.fiap.authenticacao.domain.model.valueObject.Senha;
import com.fiap.authenticacao.domain.model.valueObject.UserName;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Usuario {
    private UUID id;
    private UserName nome;
    private String matricula;
    private Senha senha;
    private Email email;

    public void atualizaSenha(Senha senha) {
        this.senha = senha;
    }
}

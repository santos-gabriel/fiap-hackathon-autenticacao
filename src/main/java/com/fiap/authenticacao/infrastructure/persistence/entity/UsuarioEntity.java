package com.fiap.authenticacao.infrastructure.persistence.entity;

import com.fiap.authenticacao.domain.model.Usuario;
import com.fiap.authenticacao.domain.model.valueObject.Email;
import com.fiap.authenticacao.domain.model.valueObject.Senha;
import com.fiap.authenticacao.domain.model.valueObject.UserName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioEntity {
    @Id
    @GeneratedValue
    @Column(columnDefinition="UUID")
    private UUID id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "nome"))
    })
    private UserName nome;

    private String matricula;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "senha"))
    })
    private Senha senha;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "email"))
    })
    private Email email;


    public Usuario to(UsuarioEntity usuarioEntity) {
        return Usuario.builder()
                .id(usuarioEntity.getId())
                .senha(usuarioEntity.getSenha())
                .matricula(usuarioEntity.getMatricula())
                .nome(usuarioEntity.getNome())
                .email(usuarioEntity.getEmail())
                .build();
    }

    public UsuarioEntity from(Usuario usuario) {
        return UsuarioEntity.builder()
                .id(usuario.getId())
                .senha(usuario.getSenha())
                .matricula(usuario.getMatricula())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .build();
    }

    public void atualizaDados(Usuario usuario) {
        this.senha = usuario.getSenha();
        this.nome = usuario.getNome();
        this.matricula = usuario.getMatricula();
        this.senha = usuario.getSenha();
        this.email = usuario.getEmail();
    }

}

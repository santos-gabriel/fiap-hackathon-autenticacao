package com.fiap.authenticacao.infrastructure.persistence.entity;

import com.fiap.authenticacao.domain.model.Usuario;
import com.fiap.authenticacao.domain.model.valueObject.Senha;
import com.fiap.authenticacao.domain.model.valueObject.UserName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "nome"))
    })
    @Embedded
    private UserName nome;
    private String matricula;
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "senha"))
    })
    @Embedded
    private Senha senha;

    public Usuario to(UsuarioEntity usuarioEntity) {
        return Usuario.builder()
                .id(usuarioEntity.getId())
                .senha(usuarioEntity.getSenha())
                .matricula(usuarioEntity.getMatricula())
                .nome(usuarioEntity.getNome())
                .build();
    }

    public UsuarioEntity from(Usuario usuario) {
        return UsuarioEntity.builder()
                .id(usuario.getId())
                .senha(usuario.getSenha())
                .matricula(usuario.getMatricula())
                .nome(usuario.getNome())
                .build();
    }
}

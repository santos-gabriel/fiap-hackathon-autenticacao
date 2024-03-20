package com.fiap.authenticacao.infrastructure.persistence.repository;

import com.fiap.authenticacao.domain.model.valueObject.Senha;
import com.fiap.authenticacao.domain.model.valueObject.UserName;
import com.fiap.authenticacao.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {
    Optional<UsuarioEntity> findByNome(UserName nome);
    Optional<UsuarioEntity> findByMatricula(String matricula);
}

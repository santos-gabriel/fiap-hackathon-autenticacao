package com.fiap.authenticacao.application.web.configuration;

import com.fiap.authenticacao.domain.ports.in.IUsuarioUseCasePort;
import com.fiap.authenticacao.domain.ports.out.IUsuarioRepositoryPort;
import com.fiap.authenticacao.domain.usecase.UsuarioUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationContext {
    @Bean
    public IUsuarioUseCasePort usuarioUseCasePort(IUsuarioRepositoryPort usuarioRepositoryPort) {
        return new UsuarioUseCase(usuarioRepositoryPort);
    }
}

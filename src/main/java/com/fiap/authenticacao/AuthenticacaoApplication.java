package com.fiap.authenticacao;

import com.fiap.authenticacao.domain.model.Usuario;
import com.fiap.authenticacao.domain.model.valueObject.Senha;
import com.fiap.authenticacao.domain.model.valueObject.UserName;
import com.fiap.authenticacao.domain.ports.in.IUsuarioUseCasePort;
import com.fiap.authenticacao.infrastructure.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class AuthenticacaoApplication {

    @Autowired
    private IUsuarioUseCasePort usuarioUseCasePort;

    public static void main(String[] args) {
        SpringApplication.run(AuthenticacaoApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        this.mockUsuario();
    }

    public void mockUsuario() {
        var usuarioJaCadastrado = usuarioUseCasePort.localizaPorNome(new UserName("user.teste"));
        if (usuarioJaCadastrado.isEmpty()) {
            var usuario01 = Usuario.builder()
                    .id(UUID.randomUUID())
                    .nome(new UserName("user.teste"))
                    .senha(new Senha("abcATest12@"))
                    .matricula("123456678")
                    .build();

            Usuario usuarioCadastrado = usuarioUseCasePort.cadastra(usuario01);
            System.out.println("Usuário cadastrado: " + usuarioCadastrado);

            var usuarioLogin = usuarioUseCasePort.realizaLogin(new Usuario(null, new UserName("user.teste"), "123456678", new Senha("abcATest12@")));
            System.out.println("Login válido: " + usuarioLogin.isPresent());
        }
    }

}

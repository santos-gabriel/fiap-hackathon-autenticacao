package com.fiap.authenticacao;

import com.fiap.authenticacao.domain.model.Usuario;
import com.fiap.authenticacao.domain.model.valueObject.Email;
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
                    .email(new Email("user@teste.com"))
                    .build();

            Usuario usuarioCadastrado = usuarioUseCasePort.cadastra(usuario01);
            System.out.println("Usuário cadastrado: " + usuarioCadastrado);

            var usuarioLogin = usuarioUseCasePort.realizaLogin(new Usuario(null, new UserName("user.teste"), "123456678", new Senha("abcATest12@"), new Email("user@teste.com")));
            System.out.println("Login válido: " + usuarioLogin.isPresent());
        }

        var usuarioJaCadastrado2 = usuarioUseCasePort.localizaPorNome(new UserName("gabriel.almeida"));
        if (usuarioJaCadastrado2.isEmpty()) {
            var usuario01 = Usuario.builder()
                    .id(UUID.randomUUID())
                    .nome(new UserName("gabriel.almeida"))
                    .senha(new Senha("gabriel123@"))
                    .matricula("123321")
                    .email(new Email("gabrielalmeidads@outlook.com"))
                    .build();

            Usuario usuarioCadastrado = usuarioUseCasePort.cadastra(usuario01);
            System.out.println("Usuário cadastrado: " + usuarioCadastrado);

            var usuarioLogin = usuarioUseCasePort.realizaLogin(new Usuario(null, new UserName("gabriel.almeida"), "123321", new Senha("gabriel123@"), new Email("gabriel@email.com")));
            System.out.println("Login válido: " + usuarioLogin.isPresent());
        }

        var usuarioJaCadastrado3 = usuarioUseCasePort.localizaPorNome(new UserName("paulo.lobo"));
        if (usuarioJaCadastrado3.isEmpty()) {
            var usuario01 = Usuario.builder()
                    .id(UUID.randomUUID())
                    .nome(new UserName("paulo.lobo"))
                    .senha(new Senha("paulo123@"))
                    .matricula("321123")
                    .email( new Email("pauloloboneto@gmail.com"))
                    .build();

            Usuario usuarioCadastrado = usuarioUseCasePort.cadastra(usuario01);
            System.out.println("Usuário cadastrado: " + usuarioCadastrado);

            var usuarioLogin = usuarioUseCasePort.realizaLogin(new Usuario(null, new UserName("paulo.lobo"), "321123", new Senha("paulo123@"), new Email("paulo@teste.com")));
            System.out.println("Login válido: " + usuarioLogin.isPresent());
        }
    }

}

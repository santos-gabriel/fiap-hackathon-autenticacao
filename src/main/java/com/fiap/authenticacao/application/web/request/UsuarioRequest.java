package com.fiap.authenticacao.application.web.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {
    private String nome;
    private String matricula;
    private String senha;
}

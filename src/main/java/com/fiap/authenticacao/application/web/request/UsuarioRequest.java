package com.fiap.authenticacao.application.web.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UsuarioRequest {
    private String nome;
    private String matricula;
    private String senha;
    private String email;
}

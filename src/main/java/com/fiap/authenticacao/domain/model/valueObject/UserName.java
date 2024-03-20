package com.fiap.authenticacao.domain.model.valueObject;

import com.fiap.authenticacao.domain.exception.LoginInvalidoException;

import java.util.Objects;

public class UserName  implements Comparable<UserName> {
    private String value;
    private static final int MINIMO_CARACTER = 5;
    private static final int MAXIMO_CARACTER = 20;

    UserName() {}

    public UserName (String valor) {
        if (Objects.nonNull(valor)) {
            this.value = valor.trim();
        }
        if (isInvalid()) {
            throw new LoginInvalidoException();
        }
    }

    public String getValue() {
        return this.value;
    }

    public boolean isValid() {
        return !isInvalid();
    }

    public boolean isInvalid() {
        if (Objects.isNull(value)) {
            return true;
        }
        boolean possuiMinimoCaracter = getValue().length() >= MINIMO_CARACTER;
        boolean possuiMaximoCaracter = getValue().length() <= MAXIMO_CARACTER;
        boolean possuiCaracterEspecialEEspaco = getValue().matches("/[^a-zA-Z0-9-.]+/g");

        return !possuiMinimoCaracter || !possuiMaximoCaracter || possuiCaracterEspecialEEspaco;
    }

    @Override
    public int compareTo(UserName o) {
        return getValue().compareTo(o.getValue());
    }

    @Override
    public String toString() {
        return getValue();
    }

}

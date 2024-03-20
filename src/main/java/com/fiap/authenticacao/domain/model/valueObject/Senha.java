package com.fiap.authenticacao.domain.model.valueObject;

import com.fiap.authenticacao.domain.exception.SenhaFracaException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Senha implements Comparable<Senha> {
    private String value;
    private static final Pattern REGEX_SENHA_VALIDA = Pattern.compile(".*[!@#$%^&*()\\[\\]{};:,.<>?\\\\/|\\-_=+].*", Pattern.CASE_INSENSITIVE);
    private static final int MINIMO_CARACTER = 8;

    Senha() {}

    public Senha (String valor) {
        if (Objects.nonNull(valor)) {
            this.value = valor.trim();
        }
        if (isInvalid()) {
            throw new SenhaFracaException();
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
        boolean possuiTamanhoMinimo = getValue().length() >= MINIMO_CARACTER;
        boolean possuiEspacos = getValue().contains(" ");
        Matcher matcher = REGEX_SENHA_VALIDA.matcher(getValue());
        boolean possuiCaracterEspecial = matcher.find();

        return !possuiTamanhoMinimo || possuiEspacos || !possuiCaracterEspecial;
    }

    @Override
    public int compareTo(Senha o) {
        return getValue().compareTo(o.getValue());
    }

    @Override
    public String toString() {
        return getValue();
    }
}

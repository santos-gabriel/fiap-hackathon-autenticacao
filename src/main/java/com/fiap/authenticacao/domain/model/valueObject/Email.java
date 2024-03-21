package com.fiap.authenticacao.domain.model.valueObject;

import com.fiap.authenticacao.domain.exception.EmailInvalidoException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {
    public static final Pattern REGEX_EMAIL_VALIDO = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private String value;

    public Email() {}

    public Email(String email) {
        this.value = Objects.nonNull(email) ? email : "";
        Matcher matcher = REGEX_EMAIL_VALIDO.matcher(email);
        if (!matcher.find()) {
            throw new EmailInvalidoException();
        }
    }

    public String getValue() {
        return this.value;
    };


    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Email other = (Email) obj;
        if (value == null) {
            return other.value == null;
        } else return value.equals(other.value);
    }
}

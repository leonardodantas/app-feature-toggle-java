package com.app.feature.toggle.app.exceptions;

import lombok.Getter;

@Getter
public class ConfigNotFoundException extends RuntimeException {

    private final String message;

    public ConfigNotFoundException(final String message) {
        this.message = message;
    }
}

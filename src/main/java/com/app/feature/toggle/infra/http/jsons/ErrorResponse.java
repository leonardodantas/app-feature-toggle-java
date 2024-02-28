package com.app.feature.toggle.infra.http.jsons;

import java.time.LocalDateTime;
import java.util.UUID;

public record ErrorResponse(
        String uuid,
        String message,
        LocalDateTime date
) {
    public static ErrorResponse from(final String message) {
        return new ErrorResponse(UUID.randomUUID().toString(), message, LocalDateTime.now());
    }
}

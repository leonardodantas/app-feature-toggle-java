package com.app.feature.toggle.infra.http.jsons;

import com.app.feature.toggle.domains.Config;

public record ConfigResponse(
        String property,
        boolean value
) {
    public static ConfigResponse from(final Config config) {
        return new ConfigResponse(config.property(), config.value());
    }
}

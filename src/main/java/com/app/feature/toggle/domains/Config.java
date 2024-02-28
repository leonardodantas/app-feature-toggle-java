package com.app.feature.toggle.domains;

public record Config(
        String id,
        String property,
        boolean value
) {
    public Config activate() {
        return new Config(this.id, this.property, true);
    }

    public Config disable() {
        return new Config(this.id, this.property, false);
    }
}

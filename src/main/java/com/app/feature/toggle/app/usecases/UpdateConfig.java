package com.app.feature.toggle.app.usecases;

import com.app.feature.toggle.app.exceptions.ConfigNotFoundException;
import com.app.feature.toggle.app.repositories.IConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateConfig {

    private final IConfigRepository configRepository;

    public void activate(final String property) {
        final var config = configRepository.findByProperty(property).orElseThrow(() -> new ConfigNotFoundException(String.format("Config %s not found", property)));
        final var configUpdate = config.activate();
        configRepository.save(configUpdate);
    }

    public void disable(final String property) {
        final var config = configRepository.findByProperty(property).orElseThrow(() -> new ConfigNotFoundException(String.format("Config %s not found", property)));
        final var configUpdate = config.disable();
        configRepository.save(configUpdate);
    }
}

package com.app.feature.toggle.app.usecases;

import com.app.feature.toggle.app.repositories.IConfigRepository;
import com.app.feature.toggle.domains.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindConfig {

    private final IConfigRepository configRepository;
    public Optional<Config> findByProperty(final String property) {
        return configRepository.findByProperty(property);
    }
}

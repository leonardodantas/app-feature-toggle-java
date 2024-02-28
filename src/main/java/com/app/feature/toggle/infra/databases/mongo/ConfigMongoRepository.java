package com.app.feature.toggle.infra.databases.mongo;

import com.app.feature.toggle.app.repositories.IConfigRepository;
import com.app.feature.toggle.domains.Config;
import com.app.feature.toggle.infra.databases.converter.ConfigDocumentConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ConfigMongoRepository implements IConfigRepository {

    private final ConfigMongoJPARepository configMongoJPARepository;
    private final ConfigDocumentConverter configDocumentConverter;

    @Override
    public Config save(final Config config) {
        final var configDocument = configMongoJPARepository.save(ConfigDocument.from(config));
        return configDocumentConverter.convert(configDocument);
    }

    @Override
    public Optional<Config> findByProperty(final String property) {
        return configMongoJPARepository.findByProperty(property)
                .map(configDocument -> {
                    final var config = configDocumentConverter.convert(configDocument);
                    return Optional.of(config);
                }).orElse(Optional.empty());
    }
}

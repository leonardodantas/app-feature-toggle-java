package com.app.feature.toggle.app.repositories;

import com.app.feature.toggle.domains.Config;

import java.util.Optional;

public interface IConfigRepository {

    Config save(final Config config);

    Optional<Config> findByProperty(final String property);
}

package com.app.feature.toggle.infra.databases.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ConfigMongoJPARepository extends MongoRepository<ConfigDocument, String> {

    Optional<ConfigDocument> findByProperty(final String property);
}

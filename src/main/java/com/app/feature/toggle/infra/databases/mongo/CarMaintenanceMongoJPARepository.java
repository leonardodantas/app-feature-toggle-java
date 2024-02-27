package com.app.feature.toggle.infra.databases.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;

public interface CarMaintenanceMongoJPARepository extends MongoRepository<CarMaintenanceDocument, String> {
    Collection<CarMaintenanceDocument> findAllByCarPlate(final String carPlate);
}

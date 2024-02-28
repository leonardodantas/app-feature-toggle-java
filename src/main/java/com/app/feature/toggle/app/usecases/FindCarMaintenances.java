package com.app.feature.toggle.app.usecases;

import com.app.feature.toggle.app.repositories.ICarMaintenancesRepository;
import com.app.feature.toggle.domains.CarMaintenance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class FindCarMaintenances {

    private final ICarMaintenancesRepository carMaintenanceMongoRepository;
    private final ICarMaintenancesRepository carMaintenancePostgresRepository;
    private final FindConfig findConfig;

    private static final String DATABASE_MONGO_ACTIVE_PROPERTY = "database.mongo.active";

    public Collection<CarMaintenance> findByCarPlate(final String carPlate) {

        final var config = findConfig.findByProperty(DATABASE_MONGO_ACTIVE_PROPERTY)
                .orElseThrow();

        if (config.value()) {
            return carMaintenanceMongoRepository.findByCarPlate(carPlate);
        }
        return carMaintenancePostgresRepository.findByCarPlate(carPlate);
    }
}

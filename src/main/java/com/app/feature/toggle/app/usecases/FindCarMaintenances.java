package com.app.feature.toggle.app.usecases;

import com.app.feature.toggle.app.repositories.ICarMaintenancesRepository;
import com.app.feature.toggle.domains.CarMaintenance;
import io.getunleash.Unleash;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class FindCarMaintenances {

    private final ICarMaintenancesRepository carMaintenanceMongoRepository;
    private final ICarMaintenancesRepository carMaintenancePostgresRepository;
    private final Unleash unleash;
    private static final String DATABASE_MONGO_ACTIVE_TOGGLE = "database.mongo.active";

    public Collection<CarMaintenance> findByCarPlate(final String carPlate) {
        if (unleash.isEnabled(DATABASE_MONGO_ACTIVE_TOGGLE)) {
            return carMaintenanceMongoRepository.findByCarPlate(carPlate);
        }
        return carMaintenancePostgresRepository.findByCarPlate(carPlate);
    }
}

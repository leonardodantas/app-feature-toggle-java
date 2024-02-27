package com.app.feature.toggle.app.usecases;

import com.app.feature.toggle.app.repositories.ICarMaintenancesRepository;
import com.app.feature.toggle.domains.CarMaintenance;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class FindCarMaintenances {

    private final ICarMaintenancesRepository carMaintenanceMongoRepository;
    private final ICarMaintenancesRepository carMaintenancePostgresRepository;
    @Value("${database.mongo.active:false}")
    private boolean databaseMongo;

    public Collection<CarMaintenance> findByCarPlate(final String carPlate) {
        if (databaseMongo) {
            return carMaintenanceMongoRepository.findByCarPlate(carPlate);
        }
        return carMaintenancePostgresRepository.findByCarPlate(carPlate);
    }
}

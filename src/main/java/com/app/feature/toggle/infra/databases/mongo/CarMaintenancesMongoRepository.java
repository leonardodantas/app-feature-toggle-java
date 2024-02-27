package com.app.feature.toggle.infra.databases.mongo;

import com.app.feature.toggle.app.repositories.ICarMaintenancesRepository;
import com.app.feature.toggle.domains.CarMaintenance;
import com.app.feature.toggle.infra.databases.converter.CarMaintenanceConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@RequiredArgsConstructor
@Component("carMaintenanceMongoRepository")
public class CarMaintenancesMongoRepository implements ICarMaintenancesRepository {

    private final CarMaintenanceMongoJPARepository carMaintenanceMongoJPARepository;
    private final CarMaintenanceConverter carMaintenanceConverter;

    @Override
    public Collection<CarMaintenance> findByCarPlate(final String carPlate) {
        return carMaintenanceMongoJPARepository.findAllByCarPlate(carPlate)
                .stream().map(carMaintenanceConverter::convert)
                .toList();
    }
}

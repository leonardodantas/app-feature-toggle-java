package com.app.feature.toggle.infra.databases.postgres;

import com.app.feature.toggle.app.repositories.ICarMaintenancesRepository;
import com.app.feature.toggle.domains.CarMaintenance;
import com.app.feature.toggle.infra.databases.converter.CarMaintenanceConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@RequiredArgsConstructor
@Component("carMaintenancePostgresRepository")
public class CarMaintenancesPostgresRepository implements ICarMaintenancesRepository {

    private final CarMaintenancesPostgresJPARepository carMaintenancesPostgresJPARepository;
    private final CarMaintenanceConverter carMaintenanceConverter;

    @Override
    public Collection<CarMaintenance> findByCarPlate(final String carPlate) {
        return carMaintenancesPostgresJPARepository.findAllByCarPlate(carPlate)
                .stream().map(carMaintenanceConverter::convert)
                .toList();
    }
}

package com.app.feature.toggle.infra.databases.converter;

import com.app.feature.toggle.domains.CarMaintenance;
import com.app.feature.toggle.domains.ICarMaintenance;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CarMaintenanceConverter implements Converter<ICarMaintenance, CarMaintenance> {

    @Override
    public CarMaintenance convert(final ICarMaintenance carMaintenance) {
        return new CarMaintenance(
                carMaintenance.getCode(),
                carMaintenance.getDescription(),
                carMaintenance.getCarPlate(),
                carMaintenance.getDataOrigin(),
                carMaintenance.getDate());
    }
}

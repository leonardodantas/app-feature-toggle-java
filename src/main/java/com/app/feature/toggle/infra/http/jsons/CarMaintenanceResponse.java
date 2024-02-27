package com.app.feature.toggle.infra.http.jsons;

import com.app.feature.toggle.domains.CarMaintenance;

import java.time.LocalDate;

public record CarMaintenanceResponse(
        String code,
        String description,
        String carPlate,
        String dataOrigin,
        LocalDate date
) {
    public static CarMaintenanceResponse from(final CarMaintenance carMaintenance) {
        return new CarMaintenanceResponse(carMaintenance.code(), carMaintenance.description(), carMaintenance.carPlate(), carMaintenance.dataOrigin(), carMaintenance.date());
    }
}

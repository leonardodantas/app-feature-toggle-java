package com.app.feature.toggle.infra.http.jsons;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

public record CarResponse(
        String id,
        String name,
        BigDecimal currentValue,
        String manufacturer,
        String plate,
        LocalDate yearManufacture,
        Collection<CarMaintenanceResponse> maintenances
) {
}

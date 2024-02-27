package com.app.feature.toggle.domains;

import java.time.LocalDate;

public record CarMaintenance(
        String code,
        String description,
        String carPlate,
        String dataOrigin,
        LocalDate date
) {
}

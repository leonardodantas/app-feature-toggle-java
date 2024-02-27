package com.app.feature.toggle.domains;

import java.time.LocalDate;

public interface ICarMaintenance {

    String getCode();

    String getDescription();

    String getCarPlate();
    String getDataOrigin();

    LocalDate getDate();
}

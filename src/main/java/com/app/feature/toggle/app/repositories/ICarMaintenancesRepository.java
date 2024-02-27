package com.app.feature.toggle.app.repositories;

import com.app.feature.toggle.domains.CarMaintenance;

import java.util.Collection;

public interface ICarMaintenancesRepository {

    Collection<CarMaintenance> findByCarPlate(final String carPlate);
}

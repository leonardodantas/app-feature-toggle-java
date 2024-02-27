package com.app.feature.toggle.infra.databases.postgres;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface CarMaintenancesPostgresJPARepository extends JpaRepository<CarMaintenanceEntity, String> {

    Collection<CarMaintenanceEntity> findAllByCarPlate(final String carPlate);
}

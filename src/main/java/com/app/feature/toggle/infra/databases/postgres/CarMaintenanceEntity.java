package com.app.feature.toggle.infra.databases.postgres;

import com.app.feature.toggle.domains.ICarMaintenance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CarMaintenance")
public class CarMaintenanceEntity implements ICarMaintenance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String code;
    @Column(length = 400)
    private String description;
    @Column(length = 20)
    private String carPlate;
    private String dataOrigin;
    private LocalDate date;
}

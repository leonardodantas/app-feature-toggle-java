package com.app.feature.toggle.infra.databases.mongo;

import com.app.feature.toggle.domains.ICarMaintenance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "CarMaintenances")
public class CarMaintenanceDocument implements ICarMaintenance {

    @Id
    private String id;
    private String code;
    private String description;
    @Indexed
    private String carPlate;
    private String dataOrigin;
    private LocalDate date;
}

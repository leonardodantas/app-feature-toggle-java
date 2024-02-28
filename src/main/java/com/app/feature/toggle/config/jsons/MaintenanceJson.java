package com.app.feature.toggle.config.jsons;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MaintenanceJson {
    private String id;
    private String code;
    private String description;
    private String carPlate;
    private LocalDate date;
}

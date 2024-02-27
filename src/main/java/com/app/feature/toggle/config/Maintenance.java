package com.app.feature.toggle.config;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Maintenance {

    private String id;
    private String code;
    private String description;
    private String carPlate;
    private LocalDate date;
}

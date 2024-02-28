package com.app.feature.toggle.config;

import com.app.feature.toggle.config.jsons.ConfigJson;
import com.app.feature.toggle.config.jsons.MaintenanceJson;
import com.app.feature.toggle.infra.databases.mongo.CarMaintenanceDocument;
import com.app.feature.toggle.infra.databases.mongo.CarMaintenanceMongoJPARepository;
import com.app.feature.toggle.infra.databases.mongo.ConfigDocument;
import com.app.feature.toggle.infra.databases.mongo.ConfigMongoJPARepository;
import com.app.feature.toggle.infra.databases.postgres.CarMaintenanceEntity;
import com.app.feature.toggle.infra.databases.postgres.CarMaintenancesPostgresJPARepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.Main;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class LoadingInitialDataConfig {

    private static final String MONGO_DB = "MongoDB";
    private static final String POSTGRES_SQL = "PostgreSQL";
    private final ObjectMapper objectMapper;
    private static final String MAINTENANCES_JSON = "maintenances.json";
    private static final String CONFIG_JSON = "config.json";

    private final CarMaintenanceMongoJPARepository carMaintenanceMongoJPARepository;
    private final CarMaintenancesPostgresJPARepository carMaintenancesPostgresJPARepository;
    private final ConfigMongoJPARepository configMongoJPARepository;

    @PostConstruct
    public void init() {
        carMaintenanceMongoJPARepository.deleteAll();
        carMaintenancesPostgresJPARepository.deleteAll();
        configMongoJPARepository.deleteAll();

        getJsonAsListObject(MAINTENANCES_JSON, new TypeReference<List<MaintenanceJson>>() {})
                .forEach(maintenance -> {
                    final var carMaintenanceMongo = CarMaintenanceDocument.builder()
                            .code(maintenance.getCode())
                            .description(maintenance.getDescription())
                            .carPlate(maintenance.getCarPlate())
                            .dataOrigin(MONGO_DB)
                            .date(maintenance.getDate())
                            .build();

                    carMaintenanceMongoJPARepository.save(carMaintenanceMongo);

                    final var carMaintenancePostgres = CarMaintenanceEntity.builder()
                            .code(maintenance.getCode())
                            .description(maintenance.getDescription())
                            .carPlate(maintenance.getCarPlate())
                            .dataOrigin(POSTGRES_SQL)
                            .date(maintenance.getDate())
                            .build();

                    carMaintenancesPostgresJPARepository.save(carMaintenancePostgres);
                });

        getJsonAsListObject(CONFIG_JSON, new TypeReference<List<ConfigJson>>() {})
                .forEach(config -> {
                    final var configMongo = ConfigDocument.builder()
                            .property(config.getProperty())
                            .value(config.isValue())
                            .build();
                    configMongoJPARepository.save(configMongo);
                });
    }

    private <T> List<T> getJsonAsListObject(final String file, final TypeReference<List<T>> typeReference) {
        try {
            final var path = Paths.get(Main.class.getClassLoader().getResource(file).toURI());
            final var jsonContent = Files.readString(path);

            return objectMapper.readValue(jsonContent, typeReference);
        } catch (final URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

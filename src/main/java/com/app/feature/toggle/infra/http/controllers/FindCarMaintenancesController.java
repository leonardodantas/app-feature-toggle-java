package com.app.feature.toggle.infra.http.controllers;

import com.app.feature.toggle.app.usecases.FindCarMaintenances;
import com.app.feature.toggle.infra.http.jsons.CarMaintenanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/maintenance")
public class FindCarMaintenancesController {

    private final FindCarMaintenances findCarMaintenances;

    @GetMapping("car/{plate}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<CarMaintenanceResponse> findByPlate(@PathVariable(name = "plate") final String carPlate) {
        return findCarMaintenances.findByCarPlate(carPlate)
                .stream().map(CarMaintenanceResponse::from)
                .toList();
    }

}

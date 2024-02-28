package com.app.feature.toggle.infra.http.controllers;

import com.app.feature.toggle.app.usecases.UpdateConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/config")
public class UpdateConfigController {

    private final UpdateConfig updateConfig;

    @PutMapping("property/{property}/activate")
    @ResponseStatus(HttpStatus.OK)
    public void activateProperty(@PathVariable final String property){
        updateConfig.activate(property);
    }

    @PutMapping("property/{property}/disable")
    @ResponseStatus(HttpStatus.OK)
    public void disableProperty(@PathVariable final String property){
        updateConfig.disable(property);
    }
}

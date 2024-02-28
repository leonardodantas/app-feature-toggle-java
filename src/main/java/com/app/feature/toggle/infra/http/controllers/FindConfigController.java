package com.app.feature.toggle.infra.http.controllers;

import com.app.feature.toggle.app.usecases.FindConfig;
import com.app.feature.toggle.infra.http.jsons.ConfigResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/config")
public class FindConfigController {

    private final FindConfig findConfig;

    @GetMapping("property/{property}")
    public ResponseEntity<ConfigResponse> findByProperty(@PathVariable final String property) {
        return findConfig.findByProperty(property)
                .map(config -> ResponseEntity.ok(ConfigResponse.from(config)))
                .orElse(ResponseEntity.notFound().build());
    }
}

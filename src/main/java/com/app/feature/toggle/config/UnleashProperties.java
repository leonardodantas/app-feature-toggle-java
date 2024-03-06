package com.app.feature.toggle.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "unleash")
public class UnleashProperties {

    private String appName;
    private String instanceId;
    private String unleashAPI;
    private String apiKey;
    private boolean synchronousFetchOnInitialisation;
}

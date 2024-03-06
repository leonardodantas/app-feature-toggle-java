package com.app.feature.toggle.config;

import io.getunleash.DefaultUnleash;
import io.getunleash.Unleash;
import io.getunleash.util.UnleashConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class UnleashToggleConfig {

    private final UnleashProperties unleashProperties;

    @Bean
    @Primary
    public Unleash unleash() {
        UnleashConfig config = UnleashConfig.builder()
                .appName(unleashProperties.getAppName())
                .instanceId(unleashProperties.getInstanceId())
                .unleashAPI(unleashProperties.getUnleashAPI())
                .apiKey(unleashProperties.getApiKey())
                .synchronousFetchOnInitialisation(unleashProperties.isSynchronousFetchOnInitialisation())
                .build();

        return new DefaultUnleash(config);
    }
}

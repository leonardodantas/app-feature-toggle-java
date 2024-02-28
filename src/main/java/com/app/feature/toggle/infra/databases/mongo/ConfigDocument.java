package com.app.feature.toggle.infra.databases.mongo;

import com.app.feature.toggle.domains.Config;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Config")
public class ConfigDocument {

    @Id
    private String id;
    @Indexed
    private String property;
    private boolean value;

    public static ConfigDocument from(final Config config) {
        return new ConfigDocument(config.id(), config.property(), config.value());
    }
}

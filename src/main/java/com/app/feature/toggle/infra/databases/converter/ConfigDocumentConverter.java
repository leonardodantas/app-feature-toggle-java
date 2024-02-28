package com.app.feature.toggle.infra.databases.converter;

import com.app.feature.toggle.domains.Config;
import com.app.feature.toggle.infra.databases.mongo.ConfigDocument;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ConfigDocumentConverter implements Converter<ConfigDocument, Config> {

    @Override
    public Config convert(final ConfigDocument document) {
        return new Config(document.getId(), document.getProperty(), document.isValue());
    }
}

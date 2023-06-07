package org.payneteasy.solution.configuration;

import lombok.Getter;
import org.payneteasy.solution.context.properties.ApplicationPropertiesReader;

import java.util.Optional;

@Getter
public class ApplicationFileConfiguration {

    public static final String STORAGE_PATH_PROPERTY = "application.storage-file.path";

    private final Optional<String> storagePath;

    public ApplicationFileConfiguration() {
        this.storagePath = ApplicationPropertiesReader.getPropertyOpt(STORAGE_PATH_PROPERTY);
    }
}

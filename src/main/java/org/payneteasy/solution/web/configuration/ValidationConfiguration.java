package org.payneteasy.solution.web.configuration;

import lombok.Getter;
import org.payneteasy.solution.context.properties.ApplicationPropertiesReader;

import java.util.List;

@Getter
public class ValidationConfiguration {

    public static final String MAX_FILE_SIZE_PROPERTY = "validation.file.max-size-kb";
    public static final String ACCEPTED_EXTENSION_PROPERTY = "validation.file.accepted-extension";


    private final int maxFileSizeKb;
    private final List<String> acceptedExtensions;


    public ValidationConfiguration() {
        this.maxFileSizeKb = ApplicationPropertiesReader.getIntegerProperty(MAX_FILE_SIZE_PROPERTY);
        this.acceptedExtensions = ApplicationPropertiesReader.getListProperty(ACCEPTED_EXTENSION_PROPERTY);
    }

}

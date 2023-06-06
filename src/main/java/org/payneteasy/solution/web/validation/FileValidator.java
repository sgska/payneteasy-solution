package org.payneteasy.solution.web.validation;

import org.payneteasy.solution.context.ApplicationContext;
import org.payneteasy.solution.web.configuration.ValidationConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FileValidator {

    public static final String FILE_EXTENSION_ERROR_PATTERN = "Current file extension %s not accepted.";
    public static final String FILE_SIZE_ERROR_PATTERN = "Current file size %s Kb too big.";

    private ValidationConfiguration configuration;

    private ValidationConfiguration getConfiguration() {
        if (Objects.isNull(configuration)) {
            configuration = ApplicationContext.getBean(ValidationConfiguration.class);
        }
        return configuration;
    }

    public List<String> validate(Map.Entry<String, byte[]> fileEntry) {
        var filename = fileEntry.getKey();
        var fileBytes = fileEntry.getValue();
        var fileExtension = filename.substring(filename.lastIndexOf("."));

        List< String> validationResult = new ArrayList<>();

        if (!getConfiguration().getAcceptedExtensions().contains(fileExtension)) {
            validationResult.add(String.format(FILE_EXTENSION_ERROR_PATTERN, fileExtension));
        }

        int fileSizeKb = fileBytes.length / 1024;
        if (fileSizeKb > getConfiguration().getMaxFileSizeKb()) {
            validationResult.add(String.format(FILE_SIZE_ERROR_PATTERN, fileSizeKb));
        }

        return validationResult;
    }
}

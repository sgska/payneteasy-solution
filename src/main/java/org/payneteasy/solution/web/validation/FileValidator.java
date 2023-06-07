package org.payneteasy.solution.web.validation;

import org.payneteasy.solution.context.ApplicationContext;
import org.payneteasy.solution.web.configuration.ValidationConfiguration;

import java.util.*;

public class FileValidator {

    public static final String FILE_EXTENSION_ERROR_PATTERN = "Current file extension %s not accepted.";
    public static final String FILE_SIZE_ERROR_PATTERN = "Current file size %s Kb too big.";
    public static final String FILE_ID_ERROR_PATTERN = "Current file id %s not valid.";

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

    public List<String> validateFileId(String fileId) {
        try {
            UUID success = UUID.fromString(fileId);
            return Collections.emptyList();
        } catch (Exception e) {
            return List.of(String.format(FILE_ID_ERROR_PATTERN, fileId));
        }
    }
}

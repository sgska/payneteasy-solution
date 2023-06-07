package org.payneteasy.solution.web.controller;

import org.payneteasy.solution.context.ApplicationContext;
import org.payneteasy.solution.model.FileInfo;
import org.payneteasy.solution.web.entity.RequestEntity;
import org.payneteasy.solution.web.entity.ResponseEntity;
import org.payneteasy.solution.web.validation.FileValidator;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UploadController extends AbstractFilesController {

    private FileValidator validator;

    private FileValidator getValidator() {
        if (Objects.isNull(validator)) {
            validator = ApplicationContext.getBean(FileValidator.class);
        }
        return validator;
    }

    @Override
    public ResponseEntity processPost(RequestEntity request) {
        var fileEntry = request.getFiles().entrySet().iterator().next();

        List<String> validate = getValidator().validate(fileEntry);
        if (!validate.isEmpty()) {
            return new ResponseEntity(400, toJson(validate));
        }

        var filename = fileEntry.getKey();
        var fileBytes = fileEntry.getValue();
        var generatedId = UUID.randomUUID().toString();
        int fileSizeKb = fileBytes.length / 1024;

        try {
            getFileService().saveFile(generatedId, filename, fileBytes);
        } catch (Exception e) {
            return toErrorResponse(e);
        }

        var fileInfo = new FileInfo(generatedId, fileSizeKb, filename);

        String responseBody = toJson(fileInfo);
        return new ResponseEntity(201, responseBody);
    }

    @Override
    public String getPath() {
        return "/upload";
    }
}

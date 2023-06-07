package org.payneteasy.solution.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.http.HttpStatus;
import org.payneteasy.solution.context.ApplicationContext;
import org.payneteasy.solution.service.FileService;
import org.payneteasy.solution.web.entity.RequestEntity;
import org.payneteasy.solution.web.entity.ResponseEntity;

import java.io.IOException;
import java.util.Objects;

public abstract class AbstractFilesController implements HttpController {

    private static final ResponseEntity METHOD_NOT_ALLOWED_RESPONSE =
            new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED_405, "Method Not Allowed");


    private ObjectMapper objectMapper;
    private FileService fileService;

    private ObjectMapper getObjectMapper() {
        if (Objects.isNull(objectMapper)) {
            objectMapper = ApplicationContext.getBean(ObjectMapper.class);
        }
        return objectMapper;
    }

    protected FileService getFileService() {
        if (Objects.isNull(fileService)) {
            fileService = ApplicationContext.getBean(FileService.class);
        }
        return fileService;
    }

    protected String toJson(Object object) {
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (IOException e) {
            return "";
        }
    }

    protected ResponseEntity toErrorResponse(Exception e) {
        return new ResponseEntity(500, e.toString());
    }

    @Override
    public ResponseEntity processGet(RequestEntity request) {
        return METHOD_NOT_ALLOWED_RESPONSE;
    }

    @Override
    public ResponseEntity processPost(RequestEntity request) {
        return METHOD_NOT_ALLOWED_RESPONSE;
    }
}

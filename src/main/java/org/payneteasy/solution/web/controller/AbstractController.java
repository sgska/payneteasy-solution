package org.payneteasy.solution.web.controller;

import org.eclipse.jetty.http.HttpStatus;
import org.payneteasy.solution.web.entity.ResponseEntity;

public abstract class AbstractController implements HttpController {

    private static final ResponseEntity METHOD_NOT_ALLOWED_RESPONSE =
            new ResponseEntity(HttpStatus.METHOD_NOT_ALLOWED_405, "Method Not Allowed");

    @Override
    public ResponseEntity processGet(String pathPart, String body) {
        return METHOD_NOT_ALLOWED_RESPONSE;
    }

    @Override
    public ResponseEntity processPost(String pathPart, String body) {
        return METHOD_NOT_ALLOWED_RESPONSE;
    }

}

package org.payneteasy.solution.web.controller;

import org.payneteasy.solution.web.entity.RequestEntity;
import org.payneteasy.solution.web.entity.ResponseEntity;

public interface HttpController {
    ResponseEntity processGet(RequestEntity request);

    ResponseEntity processPost(RequestEntity request);

    String getPath();

    default String getContentType(){
        return "application/json";
    }
}

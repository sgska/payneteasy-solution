package org.payneteasy.solution.web.controller;

import org.payneteasy.solution.web.entity.ResponseEntity;

public interface HttpController {
    ResponseEntity processGet(String pathPart, String body);

    ResponseEntity processPost(String pathPart, String body);

    String getPath();
}

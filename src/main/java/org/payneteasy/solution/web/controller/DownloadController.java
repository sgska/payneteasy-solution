package org.payneteasy.solution.web.controller;

import org.payneteasy.solution.web.entity.ResponseEntity;

public class DownloadController extends AbstractController {


    @Override
    public ResponseEntity processGet(String pathPart, String body) {


        return new ResponseEntity(200, pathPart);
    }

    @Override
    public String getPath() {
        return "/download/";
    }
}

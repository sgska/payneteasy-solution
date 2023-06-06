package org.payneteasy.solution.web.controller;

import org.payneteasy.solution.web.entity.RequestEntity;
import org.payneteasy.solution.web.entity.ResponseEntity;

public class FilesController extends AbstractFilesController {

    @Override
    public ResponseEntity processGet(RequestEntity request) {
        return super.processGet(request);
    }

    @Override
    public String getPath() {
        return "/files";
    }

}

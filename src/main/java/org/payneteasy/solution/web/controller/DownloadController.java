package org.payneteasy.solution.web.controller;

import org.payneteasy.solution.web.entity.RequestEntity;
import org.payneteasy.solution.web.entity.ResponseEntity;

public class DownloadController extends AbstractFilesController {


    @Override
    public ResponseEntity processGet(RequestEntity request) {


        return new ResponseEntity(request.getRequestPart());
    }

    @Override
    public String getPath() {
        return "/download/";
    }
}

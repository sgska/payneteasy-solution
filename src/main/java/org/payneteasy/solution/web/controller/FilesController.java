package org.payneteasy.solution.web.controller;

import org.payneteasy.solution.web.entity.RequestEntity;
import org.payneteasy.solution.web.entity.ResponseEntity;

public class FilesController extends AbstractFilesController {

    @Override
    public ResponseEntity processGet(RequestEntity request) {
        try {
            var filesInfo = getFileService().getFilesInfo();
            var body = toJson(filesInfo);

            return new ResponseEntity(body);
        } catch (Exception e) {
            return new ResponseEntity(500, e.toString());
        }
    }

    @Override
    public String getPath() {
        return "/files";
    }

}

package org.payneteasy.solution.web.controller;

import org.payneteasy.solution.model.FileInfo;
import org.payneteasy.solution.web.entity.RequestEntity;
import org.payneteasy.solution.web.entity.ResponseEntity;

import java.util.Optional;

public class DownloadController extends AbstractFilesController {


    @Override
    public ResponseEntity processGet(RequestEntity request) {
        var fileId = request.getRequestPart();

        Optional<FileInfo> fileInfoOpt;
        try {
            fileInfoOpt = getFileService().getFileInfo(fileId);
        } catch (Exception e) {
            return toErrorResponse(e);
        }
        if (fileInfoOpt.isEmpty()) {
            return new ResponseEntity(404, "File not found.");
        }

        FileInfo fileInfo = fileInfoOpt.get();
        byte[] fileBytes;
        try {
            fileBytes = getFileService().getFileData(fileInfo);
        } catch (Exception e) {
            return toErrorResponse(e);
        }

        return new ResponseEntity(fileInfo.getName(), fileBytes);
    }

    @Override
    public String getPath() {
        return "/download/";
    }

    @Override
    public String getContentType() {
        return "text/plain";
    }
}

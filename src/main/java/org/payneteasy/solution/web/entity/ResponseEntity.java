package org.payneteasy.solution.web.entity;

import lombok.Getter;

@Getter
public class ResponseEntity {

    private int httpStatus;
    private String body;

    private String fileName;
    private byte[] data;

    public ResponseEntity(int httpStatus, String body) {
        this.httpStatus = httpStatus;
        this.body = body;
    }

    public ResponseEntity(String fileName, byte[] data) {
        this.httpStatus = 200;
        this.fileName = fileName;
        this.data = data;
    }

    public ResponseEntity(String body) {
        this.httpStatus = 200;
        this.body = body;
    }
}

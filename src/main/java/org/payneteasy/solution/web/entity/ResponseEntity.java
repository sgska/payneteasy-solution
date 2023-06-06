package org.payneteasy.solution.web.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseEntity {

    private int httpStatus;
    private String body;

}

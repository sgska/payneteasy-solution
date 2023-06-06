package org.payneteasy.solution.web.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class RequestEntity {

    private String requestPath;
    private String requestPart;
    private String body;
    private Map<String, byte[]> files;

}

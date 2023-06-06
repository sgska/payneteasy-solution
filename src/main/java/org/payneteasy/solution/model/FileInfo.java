package org.payneteasy.solution.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FileInfo {

    private String id;
    private Integer size;
    private String name;

}

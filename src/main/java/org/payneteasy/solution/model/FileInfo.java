package org.payneteasy.solution.model;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FileInfo {

    private String id;
    private Long size;
    private String name;


    public FileInfo(String id, Integer size, String name) {
        this.id = id;
        this.size = size.longValue();
        this.name = name;
    }
}

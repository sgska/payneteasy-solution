package org.payneteasy.solution.storage;

import java.nio.file.Path;

public interface FileStorage {


    void save(Path filePath, byte[] data);

    byte[] read(Path filePath);

    void delete(Path filePath);
}
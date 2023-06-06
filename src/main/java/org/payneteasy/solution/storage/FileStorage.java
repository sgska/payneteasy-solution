package org.payneteasy.solution.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public interface FileStorage {


    void save(Path filePath, byte[] data) throws IOException;

    byte[] read(Path filePath) throws IOException;

    Map<Path, Long> getFiles(Path filePath) throws IOException;

    void delete(Path filePath) throws IOException;
}
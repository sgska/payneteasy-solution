package org.payneteasy.solution.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class FileSystemStorage implements FileStorage {


    @Override
    public void save(Path filePath, byte[] data) throws IOException {
        Files.write(filePath, data, StandardOpenOption.CREATE);
    }

    @Override
    public byte[] read(Path filePath) throws IOException {
        return Files.readAllBytes(filePath);
    }

    @Override
    public Map<Path, Long> getFiles(Path filePath) throws IOException {
        Map<Path, Long> result = new HashMap<>();
        File folder = filePath.toFile();

        if (!folder.exists() || !folder.isDirectory()) {
            return result;
        }

        scanFolder(folder, result);

        return result;
    }


    @Override
    public void delete(Path filePath) throws IOException {
        Files.deleteIfExists(filePath);
    }

    private static void scanFolder(File folder, Map<Path, Long> filePaths) {
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    filePaths.put(file.toPath(), file.length());
                } else if (file.isDirectory()) {
                    scanFolder(file, filePaths);
                }
            }
        }
    }
}

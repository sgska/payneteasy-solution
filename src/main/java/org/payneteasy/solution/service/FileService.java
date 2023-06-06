package org.payneteasy.solution.service;

import org.payneteasy.solution.context.ApplicationContext;
import org.payneteasy.solution.model.FileInfo;
import org.payneteasy.solution.storage.FileStorage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class FileService {

    protected static Path STORAGE_DIR;

    static {
        try {
//            STORAGE_DIR = Files.createTempDirectory("file-storage");
            STORAGE_DIR = Path.of("/var/folders/n_/nfv59zb13wv5qxpr1cmr70mc0000gn/T/file-storage2302997810393447745");
        } catch (Exception e) {
            throw new RuntimeException("Error init storage.", e);
        }
    }

    private FileStorage fileStorage;

    private FileStorage getFileStorage() {
        if (Objects.isNull(fileStorage)) {
            fileStorage = ApplicationContext.getBean(FileStorage.class);
        }

        return fileStorage;
    }

    public void saveFile(String generatedId, String filename, byte[] fileBytes) throws IOException {
        var dirPath = Path.of(STORAGE_DIR.toString(), generatedId);
        dirPath.toFile().mkdirs();

        var fullPath = Path.of(STORAGE_DIR.toString(), generatedId, filename);
        System.out.println("full path: " + fullPath);

        getFileStorage().save(fullPath, fileBytes);
    }

    public List<FileInfo> getFilesInfo() throws IOException {
        return getFileStorage().getFiles(STORAGE_DIR).entrySet().stream()
                .map(entry -> {
                    Path path = entry.getKey();
                    long fileSizeKb = entry.getValue() / 1024;
                    var parent = path.getParent().getFileName();

                    return new FileInfo(parent.toString(), fileSizeKb, path.getFileName().toString());
                })
                .toList();
    }


}

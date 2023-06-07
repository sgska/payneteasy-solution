package org.payneteasy.solution.service;

import org.payneteasy.solution.configuration.ApplicationFileConfiguration;
import org.payneteasy.solution.context.ApplicationContext;
import org.payneteasy.solution.model.FileInfo;
import org.payneteasy.solution.storage.FileStorage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FileService {

    protected String storageDir;
    private String getStorageDir() {
        if (Objects.isNull(storageDir)) {
            try {
                var configuration = ApplicationContext.getBean(ApplicationFileConfiguration.class);
                storageDir = configuration.getStoragePath()
                        .orElse(Files.createTempDirectory("file-storage").toString());
            } catch (Exception e) {
                throw new RuntimeException("Error init storage.", e);
            }
        }
        return storageDir;
    }

    private FileStorage fileStorage;
    private FileStorage getFileStorage() {
        if (Objects.isNull(fileStorage)) {
            fileStorage = ApplicationContext.getBean(FileStorage.class);
        }

        return fileStorage;
    }

    public void saveFile(String generatedId, String filename, byte[] fileBytes) throws IOException {
        var dirPath = Path.of(getStorageDir(), generatedId);
        boolean mkdirs = dirPath.toFile().mkdirs();
        if (mkdirs) {
            System.out.println("Files dir created: " + dirPath);
        }

        var fullPath = Path.of(getStorageDir(), generatedId, filename);
        System.out.println("full path: " + fullPath);

        getFileStorage().save(fullPath, fileBytes);
    }

    public List<FileInfo> getFilesInfo() throws IOException {
        return getFileStorage().getFiles(Path.of(getStorageDir())).entrySet().stream()
                .map(FileService::getFileInfoFromEntry)
                .toList();
    }


    public Optional<FileInfo> getFileInfo(String fileId) throws IOException {
        Map<Path, Long> fileMap = getFileStorage().getFiles(Path.of(getStorageDir().toString(), fileId));
        if (fileMap.isEmpty()) {
            return Optional.empty();
        }
        Map.Entry<Path, Long> fileEntry = fileMap.entrySet().iterator().next();

        return Optional.of(getFileInfoFromEntry(fileEntry));
    }

    public byte[] getFileData(FileInfo fileInfo) throws IOException {
        return getFileStorage().read(Path.of(getStorageDir(), fileInfo.getId(), fileInfo.getName()));
    }

    private static FileInfo getFileInfoFromEntry(Map.Entry<Path, Long> entry) {
        Path path = entry.getKey();
        long fileSizeKb = entry.getValue() / 1024;
        var parent = path.getParent().getFileName();

        return new FileInfo(parent.toString(), fileSizeKb, path.getFileName().toString());
    }
}

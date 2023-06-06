package org.payneteasy.solution.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileSystemStorage implements FileStorage {


    @Override
    public void save(Path filePath, byte[] data) {
        try {
            Files.write(filePath, data, StandardOpenOption.CREATE);
        } catch (IOException e) {
            // Обработка ошибок записи файла
        }
    }

    @Override
    public byte[] read(Path filePath) {
        try {
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            // Обработка ошибок чтения файла
            return null;
        }
    }

    @Override
    public void delete(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Обработка ошибок удаления файла
        }
    }
}

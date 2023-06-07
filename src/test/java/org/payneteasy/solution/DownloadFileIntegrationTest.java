package org.payneteasy.solution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.payneteasy.solution.configuration.ApplicationFileConfiguration;
import org.payneteasy.solution.context.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DownloadFileIntegrationTest extends AbstractIntegrationTest {

    private final String fileStorageMockFolder = "/var/folders/n_/nfv59zb13wv5qxpr1cmr70mc0000gn/T/file-storage-test-download";


    @BeforeEach
    void setUp() {
        File storageFIle = Path.of(fileStorageMockFolder).toFile();
        if (!storageFIle.exists()) {
            boolean mkdirs = storageFIle.mkdirs();
            if (mkdirs) {
                System.out.println("Created test storage folder.");
            }
        }
        var mockFileConfiguration = new ApplicationFileConfiguration() {
            @Override
            public Optional<String> getStoragePath() {
                return Optional.of(fileStorageMockFolder);
            }
        };

        ApplicationContext
                .getBeanContainer()
                .registerBean(ApplicationFileConfiguration.class, mockFileConfiguration);

        super.setUp();
    }

    @Test
    void downloadFile_SuccessCase() throws IOException {
        var filename = "filename.txt";
        var fileTextBytes = """
                test info text.
                """
                .getBytes(StandardCharsets.UTF_8);

        String fileId = "c2754795-d311-4d76-9d2c-ec298ec18a19";
        Path fileInStorage = Path.of(fileStorageMockFolder, fileId, filename);
        Path.of(fileStorageMockFolder, fileId).toFile().mkdirs();
        Files.write(fileInStorage, fileTextBytes, StandardOpenOption.CREATE);

        byte[] downloaded = downloadFile(fileId, 200);

        assertEquals(new String(fileTextBytes), new String(downloaded));
    }

    @Test
    void downloadFile_FileNotFound() throws IOException {

        byte[] downloaded = downloadFile(UUID.randomUUID().toString(), 404);

        assertEquals("File not found.", new String(downloaded));
    }

    @Test
    void downloadFile_NotValidFileId() throws IOException {
        byte[] downloaded = downloadFile("12345", 400);

        assertEquals("[\"Current file id 12345 not valid.\"]", new String(downloaded));
    }

    @Test
    void downloadFile_NullFileId() throws IOException {
        byte[] downloaded = downloadFile(null, 400);

        assertEquals("[\"Current file id null not valid.\"]", new String(downloaded));
    }

}

package org.payneteasy.solution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.payneteasy.solution.configuration.ApplicationFileConfiguration;
import org.payneteasy.solution.context.ApplicationContext;
import org.payneteasy.solution.model.FileInfo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilesIntegrationTest extends AbstractIntegrationTest {

    private final String fileStorageMockFolder = "/var/folders/n_/nfv59zb13wv5qxpr1cmr70mc0000gn/T/file-storage-test-files";


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
    void getFiles_SuccessCase() throws IOException {
        var filename1 = "filename1.txt";
        var filename2 = "filename2.txt";
        var fileTextBytes = """
                test1 info text.
                """
                .getBytes(StandardCharsets.UTF_8);

        String fileId1 = "c2754795-d311-4d76-9d2c-ec298ec18a19";
        String fileId2 = "c2754795-d311-4d76-9d2c-ec299ec18a19";
        saveFile(fileStorageMockFolder, fileId1, filename1, fileTextBytes);
        saveFile(fileStorageMockFolder, fileId2, filename2, fileTextBytes);

        var responseBody = getFiles(200);

        List<FileInfo> responseList = objectMapper.readerForListOf(FileInfo.class).readValue(responseBody);

        List<FileInfo> expectedList = List.of(
                new FileInfo(fileId1, 0, filename1),
                new FileInfo(fileId2, 0, filename2)
        );

        assertTrue(expectedList.containsAll(responseList));
    }


}

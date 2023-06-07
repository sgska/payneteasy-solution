package org.payneteasy.solution;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.payneteasy.solution.context.ApplicationContext;
import org.payneteasy.solution.model.FileInfo;
import org.payneteasy.solution.service.FileService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class UploadIntegrationTest extends AbstractIntegrationTest {

    @BeforeEach
    void setUp() {
        super.setUp();
    }

    private FileService getFileService() {
        return ApplicationContext.getBean(FileService.class);
    }

    @Test
    public void testFileUpload_SuccessCase() throws IOException {
        var filename = "filename.txt";
        var fileTextBytes = """
                test info text.
                """
                .getBytes(StandardCharsets.UTF_8);

        var responseBody = uploadFile(filename, fileTextBytes, 201);

        FileInfo fileInfo = objectMapper.readValue(responseBody, FileInfo.class);

        assertNotNull(fileInfo.getId());
        assertEquals(filename, fileInfo.getName());
        assertEquals(0, fileInfo.getSize());

        var createdFileOnStorage = Path.of(
                getFileService().getStorageDir(),
                fileInfo.getId(),
                fileInfo.getName());

        assertTrue(createdFileOnStorage.toFile().exists());
        var createdFileBytes = Files.readAllBytes(createdFileOnStorage);

        assertEquals(new String(fileTextBytes), new String(createdFileBytes));
    }

    @Test
    public void testFileUpload_NotValidExtension() throws IOException {
        var filename = "filename.exe";
        var fileTextBytes = """
                test info text.
                """
                .getBytes(StandardCharsets.UTF_8);

        var responseBody = uploadFile(filename, fileTextBytes, 400);

        assertEquals("[\"Current file extension .exe not accepted.\"]", responseBody);
    }

    @Test
    public void testFileUpload_BigFile() throws IOException {
        var filename = "filename.txt";

        var fileTextBytes = IOUtils.resourceToByteArray("big_file.txt", this.getClass().getClassLoader());

        var responseBody = uploadFile(filename, fileTextBytes, 400);

        assertEquals("[\"Current file size 185 Kb too big.\"]", responseBody);
    }

    @Test
    public void testFileUpload_BigFileAndInvalidExtension() throws IOException {
        var filename = "filename.exe";

        var fileTextBytes = IOUtils.resourceToByteArray("big_file.txt", this.getClass().getClassLoader());

        var responseBody = uploadFile(filename, fileTextBytes, 400);

        assertEquals("[\"Current file extension .exe not accepted.\",\"Current file size 185 Kb too big.\"]", responseBody);
    }

}

package org.payneteasy.solution;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.payneteasy.solution.context.ApplicationContext;
import org.payneteasy.solution.service.FileService;
import org.payneteasy.solution.web.configuration.HttpServerConfiguration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.assertEquals;


public abstract class AbstractIntegrationTest {

    private static final String UPLOAD_PATH = "/upload";
    private static final String FILES_PATH = "/files";
    private static final String DOWNLOAD_PATH = "/download";


    protected String fileDir;
    protected ObjectMapper objectMapper;
    protected String appUrl;

    @BeforeAll
    static void beforeAll() {
        Application.main(null);

    }

    @AfterAll
    static void afterAll() {
        if (ApplicationContext.isRunning()) {
            ApplicationContext.stop();
        }
    }

    void setUp() {
        fileDir = ApplicationContext
                .getBean(FileService.class)
                .getStorageDir();

        objectMapper = ApplicationContext.
                getBean(ObjectMapper.class);

        appUrl = "http://localhost:" + ApplicationContext
                .getBean(HttpServerConfiguration.class)
                .getPort();
    }

    protected void saveFile(String folder, String fileId, String filename, byte[] fileBytes) throws IOException {
        Path fileInStorage = Path.of(folder, fileId, filename);
        Path.of(folder, fileId).toFile().mkdirs();
        Files.write(fileInStorage, fileBytes, StandardOpenOption.CREATE);
    }

    protected String uploadFile(String filename, byte[] fileBytes, int expectedStatus) throws IOException {
        var fileInputStream = new ByteArrayInputStream(fileBytes);

        HttpEntity httpEntity = MultipartEntityBuilder.create()
                .addBinaryBody("file", fileInputStream, ContentType.TEXT_PLAIN, filename)
                .build();

        HttpPost httpPost = new HttpPost(appUrl + UPLOAD_PATH);
        httpPost.setEntity(httpEntity);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(httpPost);

        String responseBody = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
        fileInputStream.close();
        System.out.println(responseBody);

        assertEquals(expectedStatus, response.getStatusLine().getStatusCode());

        return responseBody;
    }

    protected byte[] downloadFile(String fileId, int expectedStatus) throws IOException {
        HttpGet httpGet = new HttpGet(appUrl + DOWNLOAD_PATH + "/" + fileId);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(httpGet);

        assertEquals(expectedStatus, response.getStatusLine().getStatusCode());

        return IOUtils.toByteArray(response.getEntity().getContent());
    }
    protected String getFiles(int expectedStatus) throws IOException {
        HttpGet httpGet = new HttpGet(appUrl + FILES_PATH);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(httpGet);

        assertEquals(expectedStatus, response.getStatusLine().getStatusCode());

        return IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
    }
}

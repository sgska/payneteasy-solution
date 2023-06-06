package org.payneteasy.solution.web.servlet;

import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.ByteArrayOutputStream2;
import org.eclipse.jetty.util.IO;
import org.payneteasy.solution.web.controller.DownloadController;
import org.payneteasy.solution.web.controller.HttpController;
import org.payneteasy.solution.web.controller.UploadController;
import org.payneteasy.solution.web.entity.RequestEntity;
import org.payneteasy.solution.web.entity.ResponseEntity;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.*;

public class DispatcherServlet extends HttpServlet {

    private static final MultipartConfigElement MULTI_PART_CONFIG = new MultipartConfigElement("");


    private Map<String, HttpController> CONTROLLERS_MAP;

    public DispatcherServlet() {
        this.CONTROLLERS_MAP = new HashMap<>() ;

        var downloadController = new DownloadController();
        var uploadController = new UploadController();

        this.CONTROLLERS_MAP.put(downloadController.getPath(), downloadController);
        this.CONTROLLERS_MAP.put(uploadController.getPath(), uploadController);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(req, resp, HttpMethod.GET);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        processRequest(req, resp, HttpMethod.POST);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp, HttpMethod httpMethod) {
        var requestPath = req.getPathInfo();
        var controllerOpt = getController(requestPath);
        var contentType = req.getContentType();

        ResponseEntity responseEntity;
        if (controllerOpt.isPresent()) {
            var controller = controllerOpt.get();
            var requestPart = requestPath.substring(controller.getPath().length());
            String body = null;
            Map<String, byte[]> filesDataMap = null;


            if (contentType != null && contentType.startsWith("multipart/")){
                req.setAttribute(Request.MULTIPART_CONFIG_ELEMENT, MULTI_PART_CONFIG);
                filesDataMap = readFiles(req);
            } else {
                body = readBody(req);
            }

            var requestEntity = RequestEntity.builder()
                    .requestPath(requestPath)
                    .requestPart(requestPart)
                    .body(body)
                    .files(filesDataMap)
                    .build();

            responseEntity = switch (httpMethod) {
                case GET -> controller.processGet(requestEntity);
                case POST -> controller.processPost(requestEntity);
                default -> new ResponseEntity(HttpStatus.BAD_REQUEST_400, "Bad request.");
            };

        } else {
            responseEntity = new ResponseEntity(500, "Internal server error.");
        }

        controllerOpt.ifPresent(
                httpController -> resp.setContentType(httpController.getContentType())
        );

        resp.setStatus(responseEntity.getHttpStatus());
        try {
            resp.getWriter().print(responseEntity.getBody());
        } catch (IOException e) {
            //ignored
        }

    }

    private Map<String, byte[]> readFiles(HttpServletRequest req) {
        try {
            Map<String, byte[]> fileDataMap = new HashMap<>();

            Collection<Part> parts = req.getParts();
            for (Part part : parts) {
                if (part.getContentType() != null) {
                    byte[] fileBytes = readPartBytes(part);
                    fileDataMap.put(part.getSubmittedFileName(), fileBytes);
                }
            }
            return fileDataMap;
        } catch (Exception e) {
            return Collections.emptyMap();
        }

    }

    private String readBody(HttpServletRequest request) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            var reader = request.getReader();

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            return stringBuilder.toString();
        } catch (IOException e) {
            return "";
        }
    }

    private byte[] readPartBytes(Part part) throws IOException {
        ByteArrayOutputStream2 outputStream = new ByteArrayOutputStream2();
        IO.copy(part.getInputStream(), outputStream);
        return outputStream.toByteArray();
    }

    private Optional<HttpController> getController(String requestPath) {
        return CONTROLLERS_MAP.entrySet().stream()
                .filter(entry -> requestPath.startsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst();
    }
}

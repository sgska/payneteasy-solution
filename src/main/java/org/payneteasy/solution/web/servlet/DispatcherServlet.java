package org.payneteasy.solution.web.servlet;

import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpStatus;
import org.payneteasy.solution.web.controller.DownloadController;
import org.payneteasy.solution.web.controller.HttpController;
import org.payneteasy.solution.web.entity.ResponseEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DispatcherServlet extends HttpServlet {

    private Map<String, HttpController> CONTROLLERS_MAP;

    public DispatcherServlet() {
        this.CONTROLLERS_MAP = new HashMap<>() ;

        var downloadController = new DownloadController();
        this.CONTROLLERS_MAP.put(downloadController.getPath(), downloadController);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp, HttpMethod.GET);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp, HttpMethod.POST);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp, HttpMethod httpMethod) {
        var requestPath = req.getPathInfo();
        var controllerOpt = getController(requestPath);

        ResponseEntity responseEntity;
        if (controllerOpt.isPresent()) {
            var controller = controllerOpt.get();
            var requestPart = requestPath.substring(controller.getPath().length());
            var body = readBody(req);

            responseEntity = switch (httpMethod) {
                case GET -> controller.processGet(requestPart, body);
                case POST -> controller.processPost(requestPart, body);
                default -> new ResponseEntity(HttpStatus.BAD_REQUEST_400, "Bad request.");
            };

        } else {
            responseEntity = new ResponseEntity(500, "Internal server error.");
        }

        resp.setStatus(responseEntity.getHttpStatus());
        try {
            resp.getWriter().println(responseEntity.getBody());
        } catch (IOException e) {
            //ignored
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

    private Optional<HttpController> getController(String requestPath) {
        return CONTROLLERS_MAP.entrySet().stream()
                .filter(entry -> requestPath.startsWith(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst();
    }
}

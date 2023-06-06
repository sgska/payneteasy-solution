package org.payneteasy.solution.server.http;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.payneteasy.solution.context.ApplicationContext;
import org.payneteasy.solution.context.exception.HttpServerStartException;
import org.payneteasy.solution.server.HttpServer;
import org.payneteasy.solution.server.configuration.HttpServerConfiguration;

import java.util.Objects;

public class JettyServer implements HttpServer {

    private Server server;


    private HttpServerConfiguration getConfiguration() {
        return ApplicationContext.getBean(HttpServerConfiguration.class);
    }

    private Server getServer() {
        if (Objects.isNull(server)) {
            server = new Server(getConfiguration().getPort());

//        ServletHandler servletHandler = new ServletHandler();

//        servletHandler.addServletWithMapping(GetHttpServlet.class, "/");

            server.setHandler(new DefaultHandler());
        }

        return server;
    }


    @Override
    public void start() {
        try {
            getServer().start();
        } catch (Exception e) {
            throw new HttpServerStartException("Error when start Jetty server.", e);
        }
    }

}

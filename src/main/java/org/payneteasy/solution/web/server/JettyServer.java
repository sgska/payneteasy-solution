package org.payneteasy.solution.web.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.payneteasy.solution.context.ApplicationContext;
import org.payneteasy.solution.context.exception.HttpServerStartException;
import org.payneteasy.solution.web.HttpServer;
import org.payneteasy.solution.web.configuration.HttpServerConfiguration;
import org.payneteasy.solution.web.servlet.DispatcherServlet;

import java.util.Objects;

public class JettyServer implements HttpServer {

    private Server server;


    private HttpServerConfiguration getConfiguration() {
        return ApplicationContext.getBean(HttpServerConfiguration.class);
    }

    private Server getServer() {
        if (Objects.isNull(server)) {
            server = new Server(getConfiguration().getPort());

            ServletHandler servletHandler = new ServletHandler();
            servletHandler.addServletWithMapping(DispatcherServlet.class, "/*");

            server.setHandler(servletHandler);
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

    @Override
    public void stop() {
        try {
            getServer().stop();
        } catch (Exception e) {
            //ignored
        }
    }
}

package org.payneteasy.solution.web.configuration;

import lombok.Getter;
import org.payneteasy.solution.context.properties.ApplicationPropertiesReader;

@Getter
public class HttpServerConfiguration {

    private static final String SERVER_PORT_PROPERTY = "server.port";

    private final int port;


    public HttpServerConfiguration() {
        port = ApplicationPropertiesReader.getIntegerProperty(SERVER_PORT_PROPERTY);
    }

}

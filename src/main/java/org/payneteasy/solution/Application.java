package org.payneteasy.solution;


import org.payneteasy.solution.context.ApplicationContext;
import org.payneteasy.solution.context.configuration.ApplicationContextConfiguration;
import org.payneteasy.solution.context.type.BeanLoadType;
import org.payneteasy.solution.server.HttpServer;
import org.payneteasy.solution.server.configuration.HttpServerConfiguration;
import org.payneteasy.solution.storage.FileStorage;

public class Application {


    public static void main(String[] args) {
        var configuration = new ApplicationContextConfiguration.Builder()
                // init app beans
                .addBean(BeanLoadType.SPI_LOADER, FileStorage.class)
                .addBean(HttpServerConfiguration.class)

                //init web app and server beans
                .webApp(true)
                .addBean(HttpServer.class)

                .build();

        ApplicationContext.start(configuration);
    }

}
package org.payneteasy.solution;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.payneteasy.solution.configuration.ApplicationFileConfiguration;
import org.payneteasy.solution.context.ApplicationContext;
import org.payneteasy.solution.context.configuration.ApplicationContextConfiguration;
import org.payneteasy.solution.context.type.BeanLoadType;
import org.payneteasy.solution.service.FileService;
import org.payneteasy.solution.storage.FileStorage;
import org.payneteasy.solution.web.HttpServer;
import org.payneteasy.solution.web.configuration.HttpServerConfiguration;
import org.payneteasy.solution.web.configuration.ValidationConfiguration;
import org.payneteasy.solution.web.validation.FileValidator;

public class Application {


    public static void main(String[] args) {
        var configuration = new ApplicationContextConfiguration.Builder()
                // init configurations
                .addBean(HttpServerConfiguration.class)
                .addBean(ApplicationFileConfiguration.class)
                .addBean(ValidationConfiguration.class)

                // init app beans
                .addBean(BeanLoadType.SPI_LOADER, FileStorage.class)
                .addBean(FileService.class)

                //init web app and server beans
                .webApp(true)
                .addBean(HttpServer.class)
                .addBean(ObjectMapper.class)
                .addBean(FileValidator.class)

                .build();

        ApplicationContext.start(configuration);
    }

}
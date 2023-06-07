package org.payneteasy.solution.context;

import org.payneteasy.solution.context.configuration.ApplicationContextConfiguration;
import org.payneteasy.solution.context.container.BeanContainer;
import org.payneteasy.solution.context.loader.BeanLoaderFactory;
import org.payneteasy.solution.context.type.ApplicationContextStatus;
import org.payneteasy.solution.web.HttpServer;

import static org.payneteasy.solution.context.type.ApplicationContextStatus.*;

public class ApplicationContext {

    private static BeanContainer beanContainer = new BeanContainer();
    private static BeanLoaderFactory beanLoaderFactory = new BeanLoaderFactory();

    private static ApplicationContextConfiguration contextConfiguration;
    private static HttpServer httpServer;

    private volatile static ApplicationContextStatus contextStatus = STOP;


    public static void start(ApplicationContextConfiguration configuration) {
        contextConfiguration = configuration;

        contextStatus = INIT;
        start();
    }

    public static synchronized void start() {
        assert INIT.equals(contextStatus);

        contextConfiguration.getBeanConfigs()
                .forEach(beanConfig ->
                        beanLoaderFactory.getByType(beanConfig.getBeanLoadType())
                                .load(beanContainer, beanConfig.getTClass()));

        System.out.println("Started application context.");

        if (contextConfiguration.isWebApp()) {
            httpServer = getBean(HttpServer.class);
            httpServer.start();
        }

        contextStatus = RUNNING;
    }

    public static synchronized void stop() {
        assert RUNNING.equals(contextStatus);

        if (contextConfiguration.isWebApp()) {
            httpServer.stop();
        }
        beanContainer = new BeanContainer();
        beanLoaderFactory = new BeanLoaderFactory();

        contextStatus = STOP;
    }

    public static <T> T getBean(Class<T> tClass) {
        return beanContainer.getBean(tClass);
    }


    public static BeanContainer getBeanContainer() {
        return beanContainer;
    }

    public static ApplicationContextStatus getContextStatus() {
        return contextStatus;
    }

    public static boolean isRunning() {
        return RUNNING.equals(contextStatus);
    }
}

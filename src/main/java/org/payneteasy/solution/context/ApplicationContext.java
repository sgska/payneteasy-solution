package org.payneteasy.solution.context;

import org.payneteasy.solution.context.configuration.ApplicationContextConfiguration;
import org.payneteasy.solution.context.container.BeanContainer;
import org.payneteasy.solution.context.loader.BeanLoaderFactory;
import org.payneteasy.solution.server.HttpServer;

public class ApplicationContext {

    private static final BeanContainer BEAN_CONTAINER = new BeanContainer();
    private static final BeanLoaderFactory BEAN_LOADER_FACTORY = new BeanLoaderFactory();


    public static void start(ApplicationContextConfiguration configuration) {
        configuration.getBeanConfigs()
                .forEach(beanConfig ->
                        BEAN_LOADER_FACTORY.getByType(beanConfig.getBeanLoadType())
                                .load(BEAN_CONTAINER, beanConfig.getTClass()));

        if (configuration.isWebApp()) {
            HttpServer server = getBean(HttpServer.class);
            server.start();
        }

        System.out.println("1");

    }

    public static <T> T getBean(Class<T> tClass) {
        return BEAN_CONTAINER.getBean(tClass);
    }


}

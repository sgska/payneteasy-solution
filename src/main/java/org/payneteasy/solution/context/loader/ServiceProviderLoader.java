package org.payneteasy.solution.context.loader;

import org.payneteasy.solution.context.container.BeanContainer;
import org.payneteasy.solution.context.exception.InitializationBeanException;
import org.payneteasy.solution.context.type.BeanLoadType;
import org.payneteasy.solution.context.util.BeanUtils;

import java.util.ServiceLoader;

public class ServiceProviderLoader implements BeanLoader {

    /**
     * Load bean classes as Service Provider
     * @param container bean container
     * @param tClass load class
     */
    @Override
    public void load(BeanContainer container, Class<?> tClass) {
        ServiceLoader<?> loaded = ServiceLoader.load(tClass);
        var iterator = loaded.iterator();
        if (iterator.hasNext()) {
            Object realization = iterator.next();
            container.registerBean(tClass, BeanUtils.getInstance(realization.getClass()));
        } else {
            throw new InitializationBeanException("No " + tClass.getName() + " implementation found.");
        }
    }

    @Override
    public BeanLoadType getType() {
        return BeanLoadType.SPI_LOADER;
    }
}

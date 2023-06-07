package org.payneteasy.solution.context.container;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BeanContainer {
    private final Map<Class<?>, Object> beanMap;

    public BeanContainer() {
        this.beanMap = new ConcurrentHashMap<>();
    }

    public <T> void registerBean(Class<T> beanClass, Object beanInstance) {
        beanMap.put(beanClass, beanInstance);
    }

    public <T> T getBean(Class<T> beanClass) {
        Object beanInstance = beanMap.get(beanClass);
        if (beanInstance == null) {
            throw new RuntimeException("Bean of type " + beanClass.getName() + " not found");
        }
        return beanClass.cast(beanInstance);
    }

}

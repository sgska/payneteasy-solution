package org.payneteasy.solution.context.loader;

import org.payneteasy.solution.context.container.BeanContainer;
import org.payneteasy.solution.context.type.BeanLoadType;

public interface BeanLoader {
    void load(BeanContainer container, Class<?> clazz);

    BeanLoadType getType();

}

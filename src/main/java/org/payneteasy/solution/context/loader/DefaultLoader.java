package org.payneteasy.solution.context.loader;

import org.payneteasy.solution.context.container.BeanContainer;
import org.payneteasy.solution.context.exception.MissingImplementationException;
import org.payneteasy.solution.context.exception.MultipleImplementationException;
import org.payneteasy.solution.context.type.BeanLoadType;
import org.payneteasy.solution.context.util.BeanUtils;
import org.payneteasy.solution.context.util.InterfaceUtils;

public class DefaultLoader implements BeanLoader {

    /**
     * Load bean classes with no args constructor
     * @param container bean container
     * @param tClass load class
     */
    @Override
    public void load(BeanContainer container, Class<?> tClass) {
        Object instance;
        if (tClass.isInterface()) {
            var subclasses = InterfaceUtils.getSubclasses(tClass);
            if (subclasses.size() < 1) {
                throw new MissingImplementationException(tClass);
            } else if (subclasses.size() > 1) {
                throw new MultipleImplementationException(tClass, subclasses);
            }
            instance = BeanUtils.getInstance(subclasses.get(0));
        } else {
            instance = BeanUtils.getInstance(tClass);
        }
        container.registerBean(tClass, instance);
    }

    @Override
    public BeanLoadType getType() {
        return BeanLoadType.DEFAULT;
    }

}

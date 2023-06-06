package org.payneteasy.solution.context.loader;

import org.payneteasy.solution.context.exception.InitializationBeanException;
import org.payneteasy.solution.context.type.BeanLoadType;
import org.payneteasy.solution.context.util.BeanUtils;
import org.payneteasy.solution.context.util.InterfaceUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BeanLoaderFactory {

    private final Map<BeanLoadType, BeanLoader> BEAN_LOADER_MAP;


    public BeanLoaderFactory() {
        var loaderMap = new HashMap<BeanLoadType, BeanLoader>(BeanLoadType.values().length);
        var subclasses = InterfaceUtils.getSubclasses(BeanLoader.class);

        for (Class<BeanLoader> tClass : subclasses) {
            BeanLoader instance = BeanUtils.getInstance(tClass);
            loaderMap.put(instance.getType(), instance);
        }

        BEAN_LOADER_MAP = Collections.unmodifiableMap(loaderMap);
    }

    public BeanLoader getByType(BeanLoadType type) {
        return Optional.ofNullable(BEAN_LOADER_MAP.get(type))
                .orElseThrow(() ->
                        new InitializationBeanException("Inheritor for type " + type + " not initialized"));
    }
}

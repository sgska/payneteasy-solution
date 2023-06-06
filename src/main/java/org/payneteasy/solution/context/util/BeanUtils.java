package org.payneteasy.solution.context.util;

import lombok.experimental.UtilityClass;
import org.payneteasy.solution.context.exception.InitializationBeanException;

import java.lang.reflect.InvocationTargetException;

@UtilityClass
public class BeanUtils {

    public static <T> T getInstance(Class<T> tClass) {
        try {
            return tClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException |
                 InvocationTargetException | NoSuchMethodException e) {
            throw new InitializationBeanException("Error when initialization instance: " + tClass.getName(), e);
        }
    }

}

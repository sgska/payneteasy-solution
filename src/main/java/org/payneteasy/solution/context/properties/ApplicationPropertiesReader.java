package org.payneteasy.solution.context.properties;

import lombok.experimental.UtilityClass;
import org.payneteasy.solution.context.exception.MissingPropertyException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

@UtilityClass
public class ApplicationPropertiesReader {

    public static String getProperty(String key) {
        var properties = new Properties();
        try (InputStream inputStream = ApplicationPropertiesReader.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            properties.load(inputStream);
        } catch (IOException e) {
            //ignored
        }

        return Optional.ofNullable(properties.getProperty(key))
                .orElseThrow(() -> new MissingPropertyException(key));
    }

    public static Integer getIntegerProperty(String key) {
        return Integer.valueOf(getProperty(key));
    }

}

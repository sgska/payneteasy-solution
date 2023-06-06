package org.payneteasy.solution.context.exception;

import java.util.Collection;
import java.util.stream.Collectors;

public class MultipleImplementationException extends RuntimeException {

    private static final String ERROR_MESSAGE_PATTERN =
            "Multiply implementation interface: %s. Implements: %s";

    public MultipleImplementationException(Class<?> interfaceClass, Collection<? extends Class<?>> implementations) {
        super(String.format(ERROR_MESSAGE_PATTERN, interfaceClass.getName(),
                implementations.stream()
                        .map(Class::getName)
                        .collect(Collectors.joining(", "))));
    }

}

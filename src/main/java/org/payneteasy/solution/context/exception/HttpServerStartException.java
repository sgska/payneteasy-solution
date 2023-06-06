package org.payneteasy.solution.context.exception;

public class HttpServerStartException extends RuntimeException {

    public HttpServerStartException(String message, Throwable cause) {
        super(message, cause);
    }
}

package org.ifinalframework.web.exception;

import org.ifinalframework.context.exception.UnCatchException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestControllerExceptionHandlerTest {

    private final RestControllerExceptionHandler handler = new RestControllerExceptionHandler(null);
    @Test
    void handlerException() {
        assertThrows(UnCatchException.class, () -> handler.handlerException(new RuntimeException()));
    }
}
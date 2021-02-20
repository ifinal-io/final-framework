package org.ifinal.finalframework.context.aware;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.springframework.context.MessageSource;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * MessagesMessageSourceAwareTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class MessagesMessageSourceAwareTest {

    @Test
    void setMessageSource() {
        assertDoesNotThrow(() -> {
            MessagesMessageSourceAware aware = new MessagesMessageSourceAware();
            aware.setMessageSource(Mockito.mock(MessageSource.class));
        });
    }

}

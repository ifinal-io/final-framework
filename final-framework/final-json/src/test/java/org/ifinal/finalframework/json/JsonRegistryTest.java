package org.ifinal.finalframework.json;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.ifinal.finalframework.json.jackson.JacksonJsonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

/**
 * JsonRegistryTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class JsonRegistryTest {

    @Test
    void register() {
        assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                JsonRegistry.getInstance().register(null);
            }
        });

        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                JsonRegistry.getInstance().register(new JacksonJsonService());
            }
        });
    }

}

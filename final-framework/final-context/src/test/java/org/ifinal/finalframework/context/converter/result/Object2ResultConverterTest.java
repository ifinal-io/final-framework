package org.ifinal.finalframework.context.converter.result;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.ifinal.finalframework.core.annotation.result.R;

import org.junit.jupiter.api.Test;

/**
 * Object2ResultConverterTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class Object2ResultConverterTest {

    @Test
    void convert() {

        Object2ResultConverter converter = new Object2ResultConverter();

        assertNotNull(converter.convert(null));
        assertNotNull(converter.convert(1));
        assertNotNull(converter.convert(R.success()));
    }

}

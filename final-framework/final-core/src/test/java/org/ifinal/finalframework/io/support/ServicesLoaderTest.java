package org.ifinal.finalframework.io.support;

import org.junit.jupiter.api.Test;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class ServicesLoaderTest {

    @Test
    void testLoad() {
        System.out.println(ServicesLoader.load("org.ifinal.finalframework.core.service.TestService"));
    }
}
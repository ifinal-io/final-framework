package org.finalframework.core.io.support;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author likly
 * @version 1.0
 * @date 2020-08-31 16:22:12
 * @since 1.0
 */
class ServicesLoaderTest {

    @Test
    void testLoad(){
        System.out.println(ServicesLoader.load("org.finalframework.core.service.TestService"));
    }
}
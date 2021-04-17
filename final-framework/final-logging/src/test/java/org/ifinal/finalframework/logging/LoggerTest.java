package org.ifinal.finalframework.logging;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LoggerTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class LoggerTest {

    private static final Logger outter = LoggerFactory.getLogger("outter");
    private static final Logger inner = LoggerFactory.getLogger("outter.inner");

    @Test
    void logger(){
        outter.info("outter");
        inner.info("inner");
    }


}

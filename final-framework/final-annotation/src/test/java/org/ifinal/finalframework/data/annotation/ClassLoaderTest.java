package org.ifinal.finalframework.data.annotation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class ClassLoaderTest {

    @Test
    void classLoaderTest() {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        while (true) {
            logger.info("{}", classLoader);
            classLoader = classLoader.getParent();

            if (classLoader == null) {
                logger.info("{}", classLoader);
                break;
            }

        }


    }

}

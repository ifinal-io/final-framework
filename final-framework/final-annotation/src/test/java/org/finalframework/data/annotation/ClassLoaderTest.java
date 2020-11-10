package org.finalframework.data.annotation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author likly
 * @version 1.0
 * @date 2020/9/18 23:55:45
 * @since 1.0
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

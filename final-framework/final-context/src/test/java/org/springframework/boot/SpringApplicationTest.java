package org.springframework.boot;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

/**
 * SpringApplicationTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@SpringBootApplication
class SpringApplicationTest {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringApplicationTest.class);
        Class<?> mainApplicationClass = application.getMainApplicationClass();
        logger.info("mainApplicationClass={}",mainApplicationClass);
        Assertions.assertEquals(SpringApplicationTest.class,mainApplicationClass);
    }


}

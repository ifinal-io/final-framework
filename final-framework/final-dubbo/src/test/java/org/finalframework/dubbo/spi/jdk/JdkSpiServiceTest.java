package org.finalframework.dubbo.spi.jdk;

import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;

/**
 * @author likly
 * @version 1.0
 * @date 2020-08-28 04:42:25
 * @since 1.0
 */
class JdkDubboSpiServiceTest {

    @Test
    void testJdkSpi(){
        final ServiceLoader<JdkSpiService> services = ServiceLoader.load(JdkSpiService.class);
        System.out.println("Java SPI");
        for (JdkSpiService service : services) {
            System.out.println(service.helloSpi());
        }
    }

}
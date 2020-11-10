package org.finalframework.dubbo.spi.dubbo;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.jupiter.api.Test;

/**
 * @author likly
 * @version 1.0
 * @date 2020-08-28 05:29:33
 * @since 1.0
 */
class DubboSpiServiceTest {

    @Test
    void testDubboSpi(){
        final ExtensionLoader<DubboSpiService> loader = ExtensionLoader.getExtensionLoader(DubboSpiService.class);
        System.out.println("Dubbo SPI");
        final DubboSpiService hello = loader.getExtension("hello");
        System.out.println(hello.helloSpi());
        final DubboSpiService hi = loader.getExtension("hi");
        System.out.println(hi.helloSpi());

    }
}
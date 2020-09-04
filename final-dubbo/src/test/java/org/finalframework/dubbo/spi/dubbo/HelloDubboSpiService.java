package org.finalframework.dubbo.spi.dubbo;


import org.finalframework.auto.service.AutoService;

/**
 * @author likly
 * @version 1.0
 * @date 2020-08-28 04:26:49
 * @since 1.0
 */
@AutoService(value = DubboSpiService.class,name = "hello")
public class HelloDubboSpiService implements DubboSpiService {
    @Override
    public String helloSpi() {
        return "HelloSpiService";
    }
}


package org.finalframework.dubbo.spi.jdk;


import org.finalframework.auto.service.annotation.AutoService;

/**
 * @author likly
 * @version 1.0
 * @date 2020-08-28 04:26:49
 * @since 1.0
 */
@AutoService(value = JdkSpiService.class)
public class HiJdkSpiService implements JdkSpiService {
    @Override
    public String helloSpi() {
        return "HiSpiService";
    }
}


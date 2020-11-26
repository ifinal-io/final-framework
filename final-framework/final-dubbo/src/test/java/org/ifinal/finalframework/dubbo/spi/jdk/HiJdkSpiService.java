package org.ifinal.finalframework.dubbo.spi.jdk;


import org.ifinal.finalframework.auto.service.annotation.AutoService;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@AutoService(value = JdkSpiService.class)
public class HiJdkSpiService implements JdkSpiService {
    @Override
    public String helloSpi() {
        return "HiSpiService";
    }
}


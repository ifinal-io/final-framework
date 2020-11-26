package org.ifinal.finalframework.dubbo.spi.dubbo;

import org.apache.dubbo.common.extension.SPI;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SPI
public interface DubboSpiService {

    String helloSpi();

}

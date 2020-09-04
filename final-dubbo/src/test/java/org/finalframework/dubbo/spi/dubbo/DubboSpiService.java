package org.finalframework.dubbo.spi.dubbo;

import org.apache.dubbo.common.extension.SPI;

/**
 * @author likly
 * @version 1.0
 * @date 2020-08-28 04:21:38
 * @since 1.0
 */
@SPI
public interface DubboSpiService {

    String helloSpi();

}

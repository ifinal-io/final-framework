package org.ifinal.finalframework.amp;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * AmpApplication.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
public class AmpApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmpApplication.class);
    }

}

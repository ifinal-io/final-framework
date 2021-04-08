package org.springframework.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * CloudApplication.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class CloudApplication {

    @Resource
    private Registration registration;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostConstruct
    void init() {

    }

    public static void main(String[] args) {
        SpringApplication.run(CloudApplication.class, args);
    }

}

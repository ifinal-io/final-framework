package org.ifinal.finalframework.boot.autoconfigure.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.ZooKeeper;
import org.ifinal.finalframework.zookeeper.service.ZookeeperService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Configuration
@ConditionalOnClass(ZookeeperService.class)
@EnableConfigurationProperties(ZookeeperProperties.class)
public class ZookeeperAutoConfiguration {

    private final ZookeeperProperties properties;

    public ZookeeperAutoConfiguration(ZookeeperProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ZooKeeper zooKeeper() throws IOException {
        return new ZooKeeper(properties.getAddress(), properties.getTimeout(), watchedEvent -> logger.info(watchedEvent.toString()));
    }

}

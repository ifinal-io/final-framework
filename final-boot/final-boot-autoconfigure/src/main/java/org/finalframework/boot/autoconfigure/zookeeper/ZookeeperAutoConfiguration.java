package org.finalframework.boot.autoconfigure.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.finalframework.auto.spring.factory.annotation.SpringAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/17 15:29:57
 * @since 1.0
 */
@Slf4j
@Configuration
@ConditionalOnClass(ZooKeeper.class)
@SpringAutoConfiguration
@EnableConfigurationProperties(ZookeeperProperties.class)
public class ZookeeperAutoConfiguration {

    private final ZookeeperProperties properties;

    public ZookeeperAutoConfiguration(ZookeeperProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ZooKeeper zooKeeper() throws IOException {
        return new ZooKeeper(properties.getAddress(), properties.getTimeout(), new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }

}

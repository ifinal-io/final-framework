package org.finalframework.zookeeper.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/17 15:39:14
 * @since 1.0
 */
@Slf4j
class ZookeeperServiceTest {

    private static ZooKeeper zooKeeper;

    private static ZookeeperService zookeeperService;


    @BeforeAll
    static void setUp() throws Exception {
        zooKeeper = new ZooKeeper("10.248.224.18:2181", 3000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                logger.info("Zookeeper init... {}", watchedEvent.toString());
            }
        });
        ZookeeperServiceImpl zookeeperService = new ZookeeperServiceImpl();
        zookeeperService.setZooKeeper(zooKeeper);
        ZookeeperServiceTest.zookeeperService = zookeeperService;
    }


    @Test
    void getChildren() {
        List<String> children = zookeeperService.getChildren("/dubbo");
        System.out.println(children);
        logger.info("dir=/,children={}", children);
    }


}
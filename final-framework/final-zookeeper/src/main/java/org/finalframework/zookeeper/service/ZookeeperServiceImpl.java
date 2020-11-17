package org.finalframework.zookeeper.service;

import lombok.Setter;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/17 15:35:16
 * @since 1.0
 */
@Service
@ConditionalOnBean(ZooKeeper.class)
public class ZookeeperServiceImpl implements ZookeeperService {

    @Setter
    @Resource
    private ZooKeeper zooKeeper;

    @Override
    public List<String> getChildren(String path) {
        try {
            return zooKeeper.getChildren(path, false);
        } catch (Exception e) {
            throw new ZookeeperException(e);
        }
    }
}

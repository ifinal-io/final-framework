package org.ifinal.finalframework.zookeeper.service;

import java.util.List;
import javax.annotation.Resource;
import lombok.Setter;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
@ConditionalOnBean(ZooKeeper.class)
public class ZookeeperServiceImpl implements ZookeeperService {

    @Setter
    @Resource
    private ZooKeeper zooKeeper;

    @Override
    public List<String> getChildren(final String path) {

        try {
            List<String> children = zooKeeper.getChildren(path, false);
            children.sort(String::compareTo);
            return children;
        } catch (Exception e) {
            throw new ZookeeperException(e);
        }
    }

    @Override
    public void delete(final @NonNull String path, final int version) {

        try {
            zooKeeper.delete(path, version);
        } catch (Exception e) {
            throw new ZookeeperException(e);
        }
    }

}

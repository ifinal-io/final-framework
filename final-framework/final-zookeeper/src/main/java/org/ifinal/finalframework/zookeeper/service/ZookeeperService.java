package org.ifinal.finalframework.zookeeper.service;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ZookeeperService {


    List<String> getChildren(String path);

    void delete(@NonNull String path, int version);


}

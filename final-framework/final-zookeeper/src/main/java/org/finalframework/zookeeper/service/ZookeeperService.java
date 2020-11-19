package org.finalframework.zookeeper.service;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/17 15:34:36
 * @since 1.0
 */
public interface ZookeeperService {


    List<String> getChildren(String path);

    void delete(@NonNull String path, int version);


}

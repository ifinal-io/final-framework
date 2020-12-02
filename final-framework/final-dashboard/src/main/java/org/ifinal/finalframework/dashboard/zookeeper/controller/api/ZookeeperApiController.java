package org.ifinal.finalframework.dashboard.zookeeper.controller.api;

import org.apache.zookeeper.ZooKeeper;
import org.ifinal.finalframework.zookeeper.service.ZookeeperService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/zookeeper")
@ConditionalOnClass(ZooKeeper.class)
public class ZookeeperApiController {

    @Resource
    private ZookeeperService zookeeperService;

    @GetMapping("/ls")
    public List<String> ls(@RequestParam(value = "path", required = false, defaultValue = "/") String path) {
        return zookeeperService.getChildren(path);
    }

    @DeleteMapping
    public void delete(@RequestParam("path") String path, @RequestParam(value = "version", required = false, defaultValue = "-1") int version) {
        zookeeperService.delete(path, version);
    }

}

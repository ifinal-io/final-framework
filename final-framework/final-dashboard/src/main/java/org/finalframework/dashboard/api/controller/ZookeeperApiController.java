package org.finalframework.dashboard.api.controller;

import org.finalframework.zookeeper.service.ZookeeperService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/17 15:55:13
 * @since 1.0
 */
@RestController
@RequestMapping("/api/zookeeper")
@ConditionalOnBean(ZookeeperService.class)
public class ZookeeperApiController {

    @Resource
    private ZookeeperService zookeeperService;

    public ZookeeperApiController() {
        System.out.println();
    }

    @RequestMapping("/ls")
    public List<String> ls(@RequestParam(value = "path", required = false, defaultValue = "/") String path) {
        return zookeeperService.getChildren(path);
    }

}

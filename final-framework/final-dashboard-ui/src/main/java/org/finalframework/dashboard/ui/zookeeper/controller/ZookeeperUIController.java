package org.finalframework.dashboard.ui.zookeeper.controller;

import org.finalframework.dashboard.ui.annotation.Title;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/19 20:53:12
 * @since 1.0
 */
@Controller
@RequestMapping("/dashboard/zookeeper")
public class ZookeeperUIController {

    @GetMapping
    @Title("Zookeeper")
    public String zookeeper() {
        return "dashboard/zookeeper";
    }
}

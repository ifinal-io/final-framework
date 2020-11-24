package org.finalframework.dashboard.ui.redis.controller;

import org.finalframework.annotation.auth.Auth;
import org.finalframework.dashboard.ui.annotation.Menus;
import org.finalframework.dashboard.ui.annotation.Title;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/24 21:59:59
 * @since 1.0
 */
@Auth
@Controller
@RequestMapping("/dashboard/redis")
public class RedisUIController {

    @Title("Redis")
    @Menus("redis")
    @GetMapping
    public String redis() {
        return "dashboard/redis";
    }
}

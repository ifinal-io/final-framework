package org.ifinal.finalframework.dashboard.ui.redis.controller;

import org.ifinal.finalframework.web.annotation.auth.Auth;
import org.ifinal.finalframework.dashboard.ui.annotation.Menus;
import org.ifinal.finalframework.dashboard.ui.annotation.Title;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
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

package org.finalframework.redis.admin.controller;

import org.finalframework.ui.annotation.Menus;
import org.finalframework.ui.annotation.Title;
import org.finalframework.ui.annotation.UIController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author likly
 * @version 1.0
 * @date 2019-11-09 16:58:30
 * @since 1.0
 */
@UIController
@Controller
@RequestMapping("/redis")
public class RedisUIController {
    public static final Logger logger = LoggerFactory.getLogger(RedisUIController.class);

    @Title("Redis Value")
    @Menus({"redis", "value"})
    @GetMapping("/value")
    public String value() {
        return "redis/value";
    }

}


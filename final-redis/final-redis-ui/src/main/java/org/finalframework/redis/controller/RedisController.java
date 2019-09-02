package org.finalframework.redis.controller;

import org.finalframework.spring.coding.AutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author likly
 * @version 1.0
 * @date 2019-08-28 17:58:39
 * @since 1.0
 */
@AutoConfiguration
@Controller
@RequestMapping("/redis")
public class RedisController {


    @GetMapping("/{path}")
    public String redis(@PathVariable("path") String path) {
        return "redis/" + path;
    }

}

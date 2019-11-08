package org.finalframework.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author likly
 * @version 1.0
 * @date 2019-11-07 19:53:17
 * @since 1.0
 */
@Controller
@RequestMapping("/redis2")
public class RedisViewController {
    public static final Logger logger = LoggerFactory.getLogger(RedisViewController.class);

    @GetMapping("/value")
    public String redis() {
        return "redis/value";
    }
}


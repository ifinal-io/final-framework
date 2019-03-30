package org.finalframework.test.api.controller;

import org.finalframework.monitor.action.annotation.OperationAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author likly
 * @version 1.0
 * @date 2019-03-30 11:47:39
 * @since 1.0
 */
@RestController
@RequestMapping("/")
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping
    @OperationAction("访问首页")
    public String hello() {
        return "Hello";
    }
}

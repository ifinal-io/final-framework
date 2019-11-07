package org.finalframework.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author likly
 * @version 1.0
 * @date 2019-11-05 14:12:49
 * @since 1.0
 */
@RestController
@RequestMapping("/")
public class IndexController {
    public static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping
    public Long index(){
        return System.currentTimeMillis();
    }

}


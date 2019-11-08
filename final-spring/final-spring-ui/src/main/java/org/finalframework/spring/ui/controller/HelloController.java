package org.finalframework.spring.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author likly
 * @version 1.0
 * @date 2019-11-08 12:53:31
 * @since 1.0
 */
@Controller
@RequestMapping("/")
public class HelloController {
    public static final Logger logger = LoggerFactory.getLogger(HelloController.class);


    @GetMapping
    public String index() {
        return "index";
    }

}


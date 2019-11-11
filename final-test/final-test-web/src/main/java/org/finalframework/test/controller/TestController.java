package org.finalframework.test.controller;

import org.finalframework.ui.annotation.Title;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author likly
 * @version 1.0
 * @date 2019-11-09 14:17:57
 * @since 1.0
 */
@Controller
@RequestMapping("/")
public class TestController {
    public static final Logger logger = LoggerFactory.getLogger(TestController.class);


    @GetMapping({"/index", ""})
    @Title("Hello World")
    public String index(Model model) {
        model.addAttribute("test", "test");
        model.addAttribute("title", "TITLE HAHA");
        return "index";
    }

}


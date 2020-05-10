package org.finalframework.spring.dashboard.controller;

import org.finalframework.ui.annotation.Menus;
import org.finalframework.ui.annotation.Title;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author
 * @version 1.0
 * @date 2020-05-09 14:48:13
 * @since 1.0
 */
@Controller
@RequestMapping("/beans/enums")
public class EnumUIController {
    public static final Logger logger = LoggerFactory.getLogger(EnumUIController.class);

    @Title("Enums")
    @Menus({"beans", "enums", "index"})
    @GetMapping
    public String enums() {
        return "beans/enums/index";
    }

    @Title("I18N")
    @Menus({"beans", "enums", "i18n"})
    @GetMapping({"/i18n", "/i18n/{name}"})
    public String i18n(@PathVariable(value = "name", required = false) String name, Model model) {
        model.addAttribute("name", name);
        return "beans/enums/i18n";
    }


}


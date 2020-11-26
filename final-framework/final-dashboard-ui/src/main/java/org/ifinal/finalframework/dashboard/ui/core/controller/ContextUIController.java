package org.ifinal.finalframework.dashboard.ui.core.controller;

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
@Controller
@RequestMapping("/dashboard/context")
public class ContextUIController {

    @Title("Beans")
    @Menus({"context", "beans"})
    @GetMapping("/beans")
    public String beans() {
        return "dashboard/context/beans";
    }
}

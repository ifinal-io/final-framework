package org.ifinal.finalframework.dashboard.ui.devops.controller;

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
@RequestMapping("/dashboard/devops")
public class DevopsUIController {

    @Title("Hotswap")
    @Menus({"devops", "hotswap"})
    @GetMapping("/hotswap")
    public String java() {
        return "dashboard/devops/hotswap";
    }

    @Title("Logger")
    @Menus({"devops", "logger"})
    @GetMapping("/logger")
    public String logger() {
        return "dashboard/devops/logger";
    }

}

package org.finalframework.dashboard.ui.core.controller;

import org.finalframework.dashboard.ui.annotation.Menus;
import org.finalframework.dashboard.ui.annotation.Title;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/20 23:29:41
 * @since 1.0
 */
@Controller
@RequestMapping("/dashboard")
public class RequestMappingUIController {

    @Title("RequestMapping")
    @Menus("request-mapping")
    @GetMapping("/request-mapping")
    public String requestMapping() {
        return "dashboard/request-mapping";
    }
}

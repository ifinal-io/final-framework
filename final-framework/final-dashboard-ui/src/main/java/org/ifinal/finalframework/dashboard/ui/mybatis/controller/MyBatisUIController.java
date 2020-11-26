package org.ifinal.finalframework.dashboard.ui.mybatis.controller;

import org.ifinal.finalframework.dashboard.ui.annotation.Keywords;
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
@RequestMapping("/dashboard/mybatis")
public class MyBatisUIController {

    @Title("Mapper")
    @Menus({"mybatis", "mapper"})
    @GetMapping("/mapper")
    public String mapper() {
        return "mybatis/mapper";
    }

    @Title("Query")
    @Menus({"mybatis", "query"})
    @Keywords("Mybatis Annotation Query")
    @GetMapping("/query")
    public String query() {
        return "dashboard/mybatis/query";
    }

}

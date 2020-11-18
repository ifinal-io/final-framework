package org.finalframework.dashboard.ui.mybatis.controller;

import org.finalframework.dashboard.ui.annotation.Title;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/18 12:11:34
 * @since 1.0
 */
@Controller
@RequestMapping("/dashboard/mybatis")
public class MyBatisUIController {

    @Title("Mapper")
    @GetMapping("/mapper")
    public String mapper() {
        return "mybatis/mapper";
    }

}

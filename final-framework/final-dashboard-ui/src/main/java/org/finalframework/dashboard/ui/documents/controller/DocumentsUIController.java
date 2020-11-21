package org.finalframework.dashboard.ui.documents.controller;

import org.finalframework.dashboard.ui.annotation.Menus;
import org.finalframework.dashboard.ui.annotation.Title;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/20 22:27:34
 * @since 1.0
 */
@Controller
@RequestMapping("/dashboard/documents")
public class DocumentsUIController {

    @Title("Entity")
    @Menus({"documents", "entity"})
    @GetMapping("/entity")
    public String entity() {
        return "dashboard/documents/entity";
    }

    @Title("Enums")
    @Menus({"documents", "enums"})
    @GetMapping("/enums")
    public String enums() {
        return "dashboard/documents/enums";
    }

    @Title("Query")
    @Menus({"documents", "query"})
    @GetMapping("/query")
    public String query() {
        return "dashboard/documents/query";
    }

}

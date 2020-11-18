package org.finalframework.dashboard.model.controller.api;

import org.finalframework.annotation.IEntity;
import org.finalframework.annotation.IEnum;
import org.finalframework.annotation.IQuery;
import org.finalframework.annotation.IView;
import org.finalframework.io.support.ServicesLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/18 13:49:04
 * @since 1.0
 */
@RestController
@RequestMapping("/api/models")
public class ModelApiController {

    @GetMapping("/entity")
    public List<String> entity() {
        return ServicesLoader.load(IEntity.class);
    }

    @GetMapping("/enums")
    public List<String> enums() {
        return ServicesLoader.load(IEnum.class);
    }

    @GetMapping("/query")
    public List<String> query() {
        return ServicesLoader.load(IQuery.class);
    }

    @GetMapping("/view")
    public List<String> view() {
        return ServicesLoader.load(IView.class);
    }

}

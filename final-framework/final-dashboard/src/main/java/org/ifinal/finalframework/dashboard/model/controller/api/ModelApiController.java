package org.ifinal.finalframework.dashboard.model.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.ifinal.finalframework.core.annotation.IEntity;
import org.ifinal.finalframework.core.annotation.IEnum;
import org.ifinal.finalframework.core.annotation.IQuery;
import org.ifinal.finalframework.core.annotation.IView;
import org.ifinal.finalframework.io.support.ServicesLoader;

import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
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

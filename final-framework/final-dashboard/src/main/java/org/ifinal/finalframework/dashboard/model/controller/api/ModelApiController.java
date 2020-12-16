package org.ifinal.finalframework.dashboard.model.controller.api;

import java.util.List;
import org.ifinal.finalframework.io.support.ServicesLoader;
import org.ifinal.finalframework.origin.IEntity;
import org.ifinal.finalframework.origin.IEnum;
import org.ifinal.finalframework.origin.IQuery;
import org.ifinal.finalframework.origin.IView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

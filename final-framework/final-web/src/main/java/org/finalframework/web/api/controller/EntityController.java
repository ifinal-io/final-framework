package org.finalframework.web.api.controller;

import org.finalframework.annotation.IEntity;
import org.finalframework.io.support.ServicesLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/12 11:20:34
 * @since 1.0
 */
@RestController
@RequestMapping("/api/entities")
public class EntityController {
    @GetMapping
    public List<String> entities() {
        return ServicesLoader.load(IEntity.class);
    }
}

package org.finalframework.document.api.controller;

import java.util.List;
import javax.annotation.Resource;
import org.finalframework.data.mapping.Entity;
import org.finalframework.document.api.service.EntityService;
import org.finalframework.document.api.service.query.EntityQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-09 21:02:00
 * @since 1.0
 */
@RestController
@RequestMapping("/api")
public class EntityApiController {
    public static final Logger logger = LoggerFactory.getLogger(EntityApiController.class);
    @Resource
    private EntityService entityService;


    @GetMapping("/entities")
    public List<Class<?>> query(EntityQuery query) {
        return entityService.query(query);
    }

    @GetMapping("/entity")
    public Entity<?> entity(Class<?> entity) {
        return entityService.entity(entity);
    }

}


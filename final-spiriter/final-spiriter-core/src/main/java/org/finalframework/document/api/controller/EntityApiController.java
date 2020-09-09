

package org.finalframework.document.api.controller;

import java.util.List;
import javax.annotation.Resource;

import org.finalframework.data.mapping.Entity;
import org.finalframework.document.api.entity.EntityHolder;
import org.finalframework.document.api.service.EntityService;
import org.finalframework.document.api.service.query.EntityQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-09 21:02:00
 * @since 1.0
 */
@RestController
@RequestMapping("/api")
public class EntityApiController implements InitializingBean {

    public static final Logger logger = LoggerFactory.getLogger(EntityApiController.class);
    @Resource
    private EntityService entityService;
    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @GetMapping("/entities")
    public List<EntityHolder> query(EntityQuery query) {
        return entityService.query(query);
    }

    @GetMapping("/entity")
    public Entity<?> entity(Class<?> entity) {
        return entityService.entity(entity);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println();
    }
}


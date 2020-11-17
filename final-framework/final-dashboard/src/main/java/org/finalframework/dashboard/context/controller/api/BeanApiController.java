package org.finalframework.dashboard.context.controller.api;

import org.finalframework.dashboard.context.service.BeanService;
import org.finalframework.dashboard.context.service.query.BeanQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/17 19:54:38
 * @since 1.0
 */
@RestController
@RequestMapping("/api/beans")
public class BeanApiController {

    @Resource
    private BeanService beanService;

    @GetMapping
    public List<Class<?>> query(BeanQuery query) {
        return beanService.query(query);
    }

}

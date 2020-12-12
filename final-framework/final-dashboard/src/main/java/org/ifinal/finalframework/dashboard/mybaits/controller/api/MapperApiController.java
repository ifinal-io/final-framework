package org.ifinal.finalframework.dashboard.mybaits.controller.api;

import org.ifinal.finalframework.dashboard.mybaits.service.MapperService;
import org.ifinal.finalframework.dashboard.mybaits.service.query.MapperQuery;
import org.ifinal.finalframework.mybatis.mapper.AbsMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/mybatis/mappers")
public class MapperApiController {

    @Resource
    private MapperService mapperService;


    @GetMapping
    public List<Class<? extends AbsMapper>> query(final MapperQuery query) {

        return mapperService.query(query);
    }

}

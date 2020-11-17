package org.finalframework.dashboard.mybaits.controller.api;

import org.finalframework.dashboard.mybaits.service.MapperService;
import org.finalframework.dashboard.mybaits.service.query.MapperQuery;
import org.finalframework.mybatis.mapper.AbsMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/17 22:32:09
 * @since 1.0
 */
@RestController
@RequestMapping("/api/mybatis/mappers")
public class MapperApiController {

    @Resource
    private MapperService mapperService;


    @GetMapping
    public List<Class<? extends AbsMapper>> query(MapperQuery query) {
        return mapperService.query(query);
    }

}

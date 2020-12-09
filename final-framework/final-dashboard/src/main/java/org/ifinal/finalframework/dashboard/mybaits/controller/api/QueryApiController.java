package org.ifinal.finalframework.dashboard.mybaits.controller.api;

import org.ifinal.finalframework.annotation.core.IEntity;
import org.ifinal.finalframework.annotation.core.IQuery;
import org.ifinal.finalframework.mybatis.sql.SqlBound;
import org.ifinal.finalframework.mybatis.sql.provider.SqlProviderHelper;
import org.ifinal.finalframework.web.resolver.annotation.RequestJsonParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/mybatis/query")
public class QueryApiController {

    @GetMapping
    public SqlBound query(Class<? extends IEntity<?>> entity, @RequestJsonParam IQuery query) {
        return SqlProviderHelper.query(entity, query);
    }

    @GetMapping(value = "/sql")
    public String sql(Class<? extends IEntity<?>> entity, @RequestJsonParam IQuery query) {
        return query(entity, query).getSql();
    }


}

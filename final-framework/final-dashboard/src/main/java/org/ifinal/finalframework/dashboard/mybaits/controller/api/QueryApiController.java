package org.ifinal.finalframework.dashboard.mybaits.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.ifinal.finalframework.core.annotation.IEntity;
import org.ifinal.finalframework.core.annotation.IQuery;
import org.ifinal.finalframework.mybatis.sql.SqlBound;
import org.ifinal.finalframework.mybatis.sql.provider.SqlProviderHelper;
import org.ifinal.finalframework.web.annotation.bind.RequestJsonParam;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/mybatis/query")
public class QueryApiController {

    @GetMapping
    public SqlBound query(final Class<? extends IEntity<?>> entity, final @RequestJsonParam IQuery query) {

        return SqlProviderHelper.query(entity, query);
    }

    @GetMapping(value = "/sql")
    public String sql(final Class<? extends IEntity<?>> entity, final @RequestJsonParam IQuery query) {

        return query(entity, query).getSql();
    }

}

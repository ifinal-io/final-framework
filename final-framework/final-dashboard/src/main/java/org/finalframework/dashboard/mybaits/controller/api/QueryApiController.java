package org.finalframework.dashboard.mybaits.controller.api;

import org.finalframework.annotation.IEntity;
import org.finalframework.annotation.IQuery;
import org.finalframework.mybatis.sql.SqlBound;
import org.finalframework.mybatis.sql.provider.SqlProviderHelper;
import org.finalframework.web.resolver.annotation.RequestJsonParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/20 11:37:52
 * @since 1.0
 */
@RestController
@RequestMapping("/api/mybatis/query")
public class QueryApiController {


    @RequestMapping
    public SqlBound query(Class<? extends IEntity<?>> entity, @RequestJsonParam IQuery query) {
        return SqlProviderHelper.query(entity, query);
    }

    @RequestMapping("/sql")
    public String sql(Class<? extends IEntity<?>> entity, @RequestJsonParam IQuery query) {
        return query(entity, query).getSql();
    }


}

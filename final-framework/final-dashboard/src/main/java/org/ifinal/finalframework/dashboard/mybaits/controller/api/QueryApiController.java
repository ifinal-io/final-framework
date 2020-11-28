package org.ifinal.finalframework.dashboard.mybaits.controller.api;

import org.ifinal.finalframework.annotation.IEntity;
import org.ifinal.finalframework.annotation.IQuery;
import org.ifinal.finalframework.mybatis.sql.SqlBound;
import org.ifinal.finalframework.mybatis.sql.provider.SqlProviderHelper;
import org.ifinal.finalframework.web.resolver.annotation.RequestJsonParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/mybatis/query")
public class QueryApiController {


    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public SqlBound query(Class<? extends IEntity<?>> entity, @RequestJsonParam IQuery query) {
        return SqlProviderHelper.query(entity, query);
    }

    @RequestMapping(value = "/sql", method = {RequestMethod.GET, RequestMethod.POST})
    public String sql(Class<? extends IEntity<?>> entity, @RequestJsonParam IQuery query) {
        return query(entity, query).getSql();
    }


}

package org.ifinal.finalframework.dashboard.mybaits.service.impl;

import java.util.HashMap;
import java.util.Objects;
import org.ifinal.finalframework.dashboard.mybaits.service.XmlMapperService;
import org.ifinal.finalframework.dashboard.mybaits.service.query.XmlMapperQuery;
import org.ifinal.finalframework.mybatis.sql.provider.SqlProviderHelper;
import org.springframework.stereotype.Service;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
@SuppressWarnings("unused")
class XmlMapperServiceImpl implements XmlMapperService {

    @Override
    public String xml(final XmlMapperQuery query) {

        try {
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put(SqlProviderHelper.PARAMETER_NAME_TABLE, query.getTable());
            parameters.put(SqlProviderHelper.PARAMETER_NAME_IGNORE, query.isIgnore());
            parameters.put(SqlProviderHelper.PARAMETER_NAME_SELECTIVE, query.isSelective());
            if (Objects.nonNull(query.getQuery())) {
                parameters.put(SqlProviderHelper.PARAMETER_NAME_QUERY, query.getQuery().getConstructor().newInstance());
            }

            return SqlProviderHelper.xml(query.getMapper(), query.getMethod(), parameters);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}

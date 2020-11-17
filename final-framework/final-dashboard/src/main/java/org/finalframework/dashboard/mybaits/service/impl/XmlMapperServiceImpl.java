package org.finalframework.dashboard.mybaits.service.impl;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.finalframework.dashboard.mybaits.service.XmlMapperService;
import org.finalframework.dashboard.mybaits.service.query.XmlMapperQuery;
import org.finalframework.mybatis.mapper.AbsMapper;
import org.finalframework.mybatis.sql.provider.InsertSqlProvider;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/17 23:50:06
 * @since 1.0
 */
@Service
class XmlMapperServiceImpl implements XmlMapperService {


    private static final Constructor<ProviderContext> PROVIDER_CONTEXT_CONSTRUCTOR;

    /**
     * @see AbsMapper#insert(String, Class, boolean, Collection)
     */
    private static final Class<?>[] INSERT_METHOD_ARGS = new Class[]{String.class, Class.class, boolean.class, Collection.class};
    /**
     * @see AbsMapper#replace(String, Class, Collection)
     */
    private static final Class<?>[] REPLACE_METHOD_ARGS = new Class[]{String.class, Class.class, Collection.class};

    private static final Map<String, Method> METHODS = new HashMap<>();

    static {
        /**
         * @see AbsMapper#insert(String, Class, boolean, Collection)
         */
        METHODS.put("insert", ReflectionUtils.findMethod(AbsMapper.class, "insert", INSERT_METHOD_ARGS));
        METHODS.put("replace", ReflectionUtils.findMethod(AbsMapper.class, "replace", REPLACE_METHOD_ARGS));


        try {
            PROVIDER_CONTEXT_CONSTRUCTOR = ReflectionUtils.accessibleConstructor(ProviderContext.class, Class.class, Method.class, String.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String insert(XmlMapperQuery query) {
        query.setMethod(METHODS.get("insert"));
        return xml(query);
    }

    @Override
    public String replace(XmlMapperQuery query) {
        query.setMethod(METHODS.get("replace"));
        return xml(query);
    }

    @Override
    public String xml(XmlMapperQuery query) {

        try {
            final HashMap<String, Object> parameters = new HashMap<>();
            ProviderContext providerContext = PROVIDER_CONTEXT_CONSTRUCTOR.newInstance(query.getMapper(), query.getMethod(), null);
            String sql = new InsertSqlProvider().insert(providerContext, parameters);
            return sql;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

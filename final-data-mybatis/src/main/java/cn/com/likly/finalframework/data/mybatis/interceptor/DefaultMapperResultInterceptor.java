package cn.com.likly.finalframework.data.mybatis.interceptor;

import cn.com.likly.finalframework.data.mapping.Entity;
import cn.com.likly.finalframework.data.mybatis.mapping.ResultMapCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 21:36
 * @since 1.0
 */
@Intercepts({
        @Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class}),
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
@Slf4j
@Component
public class DefaultMapperResultInterceptor implements Interceptor {


    private static final Set<String> MSID = new HashSet<>(Arrays.asList(
            "DefaultMapper.selectOne",
            "DefaultMapper.select"
    ));

    @Resource
    private ResultMapCache resultMapCache;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        if (invocation.getTarget() instanceof Executor) {
            MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
            if (!MSID.contains(ms.getId())) return invocation.proceed();
            Object arg = invocation.getArgs()[1];
            if (!(arg instanceof Map)) return invocation.proceed();

            Map<String, Object> paramMap = (Map<String, Object>) arg;

            ResultMap resultMap = ms.getResultMaps().get(0);

            Field resultMapsField = MappedStatement.class.getDeclaredField("resultMaps");
            resultMapsField.setAccessible(true);
            final List<ResultMap> finalResultMaps = new ArrayList<>(ms.getResultMaps().size());
            finalResultMaps.addAll(ms.getResultMaps());
            resultMapsField.set(ms, finalResultMaps);

            Field configurationField = ResultMap.class.getDeclaredField("configuration");
            configurationField.setAccessible(true);
            Configuration configuration = (Configuration) configurationField.get(resultMap);

            Entity holder = (Entity) paramMap.get("holder");

            ResultMap finalResultMap = resultMapCache.get(holder, configuration);
            finalResultMaps.set(0, finalResultMap);

        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}

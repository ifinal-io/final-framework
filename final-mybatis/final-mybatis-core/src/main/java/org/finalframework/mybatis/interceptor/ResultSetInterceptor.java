package org.finalframework.mybatis.interceptor;


import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.finalframework.mybatis.resultset.FinalResultSetHandler;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.CallableStatement;
import java.sql.Statement;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-12 13:34:50
 * @see org.apache.ibatis.executor.resultset.ResultSetHandler
 * @since 1.0
 */
@Order
@Intercepts(
        {
                @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class}),
                @Signature(type = ResultSetHandler.class, method = "handleCursorResultSets", args = {Statement.class}),
                @Signature(type = ResultSetHandler.class, method = "handleOutputParameters", args = {CallableStatement.class}),
        }
)
@SpringComponent
public class ResultSetInterceptor implements Interceptor {

    private static final String HANDLE_RESULT_SETS = "handleResultSets";
    private static final String HANDLE_CURSOR_RESULT_SETS = "handleCursorResultSets";
    private static final String HANDLE_OUTPUT_PARAMETERS = "handleOutputParameters";


    @NonNull
    private static final Field executorField = Objects.requireNonNull(ReflectionUtils.findField(DefaultResultSetHandler.class, "executor"));
    @NonNull
    private static final Field boundSqlField = Objects.requireNonNull(ReflectionUtils.findField(DefaultResultSetHandler.class, "boundSql"));
    @NonNull
    private static final Field mappedStatementField = Objects.requireNonNull(ReflectionUtils.findField(DefaultResultSetHandler.class, "mappedStatement"));
    @NonNull
    private static final Field rowBoundsField = Objects.requireNonNull(ReflectionUtils.findField(DefaultResultSetHandler.class, "rowBounds"));
    @NonNull
    private static final Field parameterHandlerField = Objects.requireNonNull(ReflectionUtils.findField(DefaultResultSetHandler.class, "parameterHandler"));
    @NonNull
    private static final Field resultHandlerField = Objects.requireNonNull(ReflectionUtils.findField(DefaultResultSetHandler.class, "resultHandler"));


    static {
        executorField.setAccessible(true);
        mappedStatementField.setAccessible(true);
        parameterHandlerField.setAccessible(true);
        resultHandlerField.setAccessible(true);
        boundSqlField.setAccessible(true);
        rowBoundsField.setAccessible(true);
    }


    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        final DefaultResultSetHandler target = (DefaultResultSetHandler) invocation.getTarget();
        Executor executor = (Executor) executorField.get(target);
        MappedStatement mappedStatement = (MappedStatement) mappedStatementField.get(target);
        ParameterHandler parameterHandler = (ParameterHandler) parameterHandlerField.get(target);
        ResultHandler<?> resultHandler = (ResultHandler<?>) resultHandlerField.get(target);
        BoundSql boundSql = (BoundSql) boundSqlField.get(target);
        RowBounds rowBounds = (RowBounds) rowBoundsField.get(target);

        final FinalResultSetHandler resultSetHandler = new FinalResultSetHandler(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds);

        switch (invocation.getMethod().getName()) {
            case HANDLE_RESULT_SETS:
                return resultSetHandler.handleResultSets((Statement) invocation.getArgs()[0]);
            case HANDLE_CURSOR_RESULT_SETS:
                return resultSetHandler.handleCursorResultSets((Statement) invocation.getArgs()[0]);
            case HANDLE_OUTPUT_PARAMETERS:
                resultSetHandler.handleOutputParameters((CallableStatement) invocation.getArgs()[0]);
                return null;
            default:
                return invocation.proceed();
        }

    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}


package com.ilikly.finalframework.cache.interceptor;

import com.ilikly.finalframework.cache.CacheOperationExpressionEvaluator;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 15:19:40
 * @since 1.0
 */
public class DefaultCacheOperationExpressionEvaluator extends CachedExpressionEvaluator implements CacheOperationExpressionEvaluator {

    /**
     * Indicate that there is no result variable.
     */
    public static final Object NO_RESULT = new Object();

    /**
     * Indicate that the result variable cannot be used at all.
     */
    public static final Object RESULT_UNAVAILABLE = new Object();

    /**
     * The name of the variable holding the result object.
     */
    public static final String RESULT_VARIABLE = "result";

    private final Map<CachedExpressionEvaluator.ExpressionKey, Expression> keyCache = new ConcurrentHashMap<>(64);
    private final Map<CachedExpressionEvaluator.ExpressionKey, Expression> fieldCache = new ConcurrentHashMap<>(64);

    private final Map<CachedExpressionEvaluator.ExpressionKey, Expression> conditionCache = new ConcurrentHashMap<>(64);
    private final Map<CachedExpressionEvaluator.ExpressionKey, Expression> expiredCache = new ConcurrentHashMap<>(64);


    @Override
    public EvaluationContext createEvaluationContext(Method method, Object[] args, Object target, Class<?> targetClass, Method targetMethod, Object result) {

        CacheExpressionRootObject rootObject = new CacheExpressionRootObject(method, args, target, targetClass);
        CacheEvaluationContext evaluationContext = new CacheEvaluationContext(rootObject, targetMethod, args, getParameterNameDiscoverer());
        if (result == RESULT_UNAVAILABLE) {
            evaluationContext.addUnavailableVariable(RESULT_VARIABLE);
        } else if (result != NO_RESULT) {
            evaluationContext.setVariable(RESULT_VARIABLE, result);
        }

        return evaluationContext;
    }


    @Override
    public Object key(String keyExpression, AnnotatedElementKey methodKey, EvaluationContext context) {
        return getExpression(this.keyCache, methodKey, keyExpression).getValue(context);
    }

    @Override
    public Object field(String fieldExpression, AnnotatedElementKey methodKey, EvaluationContext context) {
        return getExpression(this.fieldCache, methodKey, fieldExpression).getValue(context);
    }

    @Override
    public boolean condition(String conditionExpression, AnnotatedElementKey methodKey, EvaluationContext context) {
        return (Boolean.TRUE.equals(getExpression(this.conditionCache, methodKey, conditionExpression).getValue(
                context, Boolean.class)));
    }

    @Override
    public long expired(String expiredExpression, AnnotatedElementKey methodKey, EvaluationContext context) {
        Object value = getExpression(this.expiredCache, methodKey, expiredExpression).getValue(context);
        if (value instanceof Number) {
            return ((Number) value).longValue();
        } else if (value instanceof Date) {
            return ((Date) value).getTime();
        }
        throw new IllegalArgumentException("");
    }

    @Override
    public void clear() {
        this.keyCache.clear();
        this.fieldCache.clear();
        this.conditionCache.clear();
        this.expiredCache.clear();
    }
}

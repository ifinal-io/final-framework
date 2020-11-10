package org.finalframework.cache.interceptor;

import org.finalframework.aop.interceptor.BaseOperationExpressionEvaluator;
import org.finalframework.cache.CacheOperationExpressionEvaluator;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 15:19:40
 * @since 1.0
 */
public class DefaultCacheOperationExpressionEvaluator extends BaseOperationExpressionEvaluator implements CacheOperationExpressionEvaluator {

    private final Map<ExpressionKey, Expression> keyCache = new ConcurrentHashMap<>(64);
    private final Map<ExpressionKey, Expression> fieldCache = new ConcurrentHashMap<>(64);

    private final Map<ExpressionKey, Expression> conditionCache = new ConcurrentHashMap<>(64);
    private final Map<ExpressionKey, Expression> expiredCache = new ConcurrentHashMap<>(64);

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
    public Object expired(String expiredExpression, AnnotatedElementKey methodKey, EvaluationContext context) {
        return getExpression(this.expiredCache, methodKey, expiredExpression).getValue(context);
    }

    @Override
    public void clear() {
        super.clear();
        this.keyCache.clear();
        this.fieldCache.clear();
        this.conditionCache.clear();
        this.expiredCache.clear();
    }
}

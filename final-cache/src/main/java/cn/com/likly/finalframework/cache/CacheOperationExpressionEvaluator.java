package cn.com.likly.finalframework.cache;

import org.springframework.expression.EvaluationContext;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-22 16:54:47
 * @since 1.0
 */
public interface CacheOperationExpressionEvaluator {

    Object key(String keyExpression, EvaluationContext context);

    Object field(String fieldExpression, EvaluationContext context);

    boolean condition(String conditionExpression, EvaluationContext context);

    long expired(String expiredExpression, EvaluationContext context);

    long ttl(String ttlExpression, EvaluationContext context);

    void clear();


}

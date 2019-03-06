package com.ilikly.finalframework.cache.interceptor;

import com.fasterxml.jackson.annotation.JsonView;
import com.ilikly.finalframework.cache.CacheOperation;
import com.ilikly.finalframework.cache.CacheOperationExpressionEvaluator;
import com.ilikly.finalframework.cache.CacheOperationInvocationContext;
import com.ilikly.finalframework.core.Assert;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.lang.NonNull;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * A {@link CacheOperationInvocationContext} context for a {@link CacheOperation}
 *
 * @author likly
 * @version 1.0
 * @date 2018-11-23 16:08:08
 * @since 1.0
 */
@SuppressWarnings("all")
public class CacheOperationContext implements CacheOperationInvocationContext {

    private static final String EXPRESSION_PREFIX = "{";
    private static final String EXPRESSION_SUFFIX = "}";

    private final CacheOperationExpressionEvaluator evaluator;
    private final CacheOperationMetadata metadata;
    private final Object target;
    private final Class<?> view;
    private final Object[] args;
    private Boolean conditionPassing;

    public CacheOperationContext(CacheOperationExpressionEvaluator evaluator, CacheOperationMetadata metadata, Object target, Object[] args) {
        this.evaluator = evaluator;
        this.metadata = metadata;
        this.target = target;
        this.view = extractView(metadata.getMethod());
        this.args = extractArgs(metadata.getMethod(), args);
    }

    @Override
    public CacheOperation operation() {
        return this.metadata.getOperation();
    }

    public CacheOperationMetadata metadata() {
        return metadata;
    }

    @Override
    public Object target() {
        return this.target;
    }

    @Override
    public Method method() {
        return this.metadata.getMethod();
    }

    @Override
    public Object[] args() {
        return this.args;
    }

    @Override
    public Class<?> view() {
        return view;
    }

    @Override
    public Class<?> returnType() {
        return this.metadata.getReturnType();
    }

    @Override
    public Type genericReturnType() {
        return this.metadata.getGenericReturnType();
    }


    private Object[] extractArgs(Method method, Object[] args) {
        if (!method.isVarArgs()) {
            return args;
        }
        Object[] varArgs = ObjectUtils.toObjectArray(args[args.length - 1]);
        Object[] combinedArgs = new Object[args.length - 1 + varArgs.length];
        System.arraycopy(args, 0, combinedArgs, 0, args.length - 1);
        System.arraycopy(varArgs, 0, combinedArgs, args.length - 1, varArgs.length);
        return combinedArgs;
    }

    private Class<?> extractView(Method method) {
        final JsonView jsonView = AnnotationUtils.findAnnotation(method, JsonView.class);
        if (jsonView == null) return null;
        Class<?>[] classes = jsonView.value();
        if (classes.length != 1) {
            throw new IllegalArgumentException(
                    "@JsonView only supported for cache advice with exactly 1 class argument: " + method.getDeclaringClass().getCanonicalName() + "#" + method.getName());
        }
        return classes[0];
    }

    @Override
    public EvaluationContext createEvaluationContext(Object result) {
        return evaluator.createEvaluationContext(this.metadata().getMethod(), this.args(),
                this.target(), this.metadata().getTargetClass(), this.metadata().getTargetMethod(), result);
    }

    @Override
    public Object generateKey(EvaluationContext result) {
        String[] keys = this.metadata.getOperation().key();
        String[] keyValues = Arrays.stream(keys)
                .map(key -> {
                    if (isExpression(key)) {
                        return evaluator.key(getExpression(key), this.metadata.getMethodKey(), result);
                    } else {
                        return key;
                    }
                })
                .map(Object::toString)
                .toArray(String[]::new);

        return String.join(this.metadata.getOperation().delimiter(), keyValues);
    }


    @Override
    public Object generateField(EvaluationContext result) {

        final String[] fields = this.metadata.getOperation().field();
        if (Assert.isEmpty(fields)) return null;
        String[] fieldValues = Arrays.stream(fields)
                .map(field -> {
                    if (isExpression(field)) {
                        return evaluator.field(getExpression(field), this.metadata.getMethodKey(), result);
                    } else {
                        return field;
                    }
                })
                .map(Object::toString)
                .toArray(String[]::new);

        return String.join(this.metadata.getOperation().delimiter(), fieldValues);
    }

    @Override
    public Object generateResult(EvaluationContext result) {
        final String expression = this.metadata.getOperation().result();
        if (expression != null && isExpression(expression)) {
            return evaluator.result(getExpression(expression), this.metadata.getMethodKey(), result);
        }
        return null;
    }

    @Override
    public boolean isConditionPassing(EvaluationContext result) {
        if (this.conditionPassing == null) {
            final String expression = this.metadata.getOperation().condition();
            if (expression != null && isExpression(expression)) {
                this.conditionPassing = evaluator.condition(getExpression(expression), this.metadata.getMethodKey(), result);
            } else {
                this.conditionPassing = true;
            }
        }
        return this.conditionPassing;
    }

    @Override
    public Object generateExpire(EvaluationContext result) {
        final String expression = this.metadata.getOperation().expire();
        if (expression != null && isExpression(expression)) {
            return evaluator.expired(getExpression(expression), this.metadata.getMethodKey(), result);
        }
        return null;
    }

    private boolean isExpression(String expression) {
        return StringUtils.hasText(expression) && expression.startsWith(EXPRESSION_PREFIX) && expression.endsWith(EXPRESSION_SUFFIX);
    }

    private String getExpression(@NonNull String expression) {
        return expression.substring(EXPRESSION_PREFIX.length(), expression.length() - EXPRESSION_SUFFIX.length());
    }


}

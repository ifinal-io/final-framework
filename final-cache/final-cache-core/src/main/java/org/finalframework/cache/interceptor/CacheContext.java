package org.finalframework.cache.interceptor;

import com.fasterxml.jackson.annotation.JsonView;
import org.finalframework.cache.CacheOperation;
import org.finalframework.cache.CacheOperationContext;
import org.finalframework.cache.CacheOperationExpressionEvaluator;
import org.finalframework.cache.CacheProperty;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * A {@link CacheProperty} context for a {@link CacheOperation}
 *
 * @author likly
 * @version 1.0
 * @date 2018-11-23 16:08:08
 * @since 1.0
 */
@SuppressWarnings("all")
public class CacheContext<O extends CacheOperation, T> implements CacheOperationContext<O, T> {

    private final CacheOperationMetadata<O> metadata;
    private final Object target;
    private final Class<?> view;
    private final Object[] args;
    private T invocation;

    public CacheContext(CacheOperationExpressionEvaluator evaluator, CacheOperationMetadata<O> metadata, Object target, Object[] args) {
        this.metadata = metadata;
        this.target = target;
        this.view = extractView(metadata.getMethod());
        this.args = extractArgs(metadata.getMethod(), args);
    }

    @Override
    public O operation() {
        return this.metadata.getOperation();
    }

    @Override
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
    public T property() {
        return invocation;
    }

    @Override
    public void property(T property) {
        this.invocation = property;
    }


}

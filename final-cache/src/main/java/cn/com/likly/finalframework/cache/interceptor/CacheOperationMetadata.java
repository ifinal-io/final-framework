package cn.com.likly.finalframework.cache.interceptor;

import cn.com.likly.finalframework.cache.CacheOperation;
import lombok.Data;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.core.BridgeMethodResolver;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 16:05:09
 * @since 1.0
 */
@Data
class CacheOperationMetadata<O extends CacheOperation> {

    private final O operation;

    private final Method method;

    private final Class<?> returnType;

    private final Type genericReturnType;

    private final Class<?> targetClass;

    private final Method targetMethod;

    private final AnnotatedElementKey methodKey;


    public CacheOperationMetadata(O operation, Method method, Class<?> targetClass) {
        this.operation = operation;
        this.method = BridgeMethodResolver.findBridgedMethod(method);
        this.returnType = method.getReturnType();
        this.genericReturnType = method.getGenericReturnType();
        this.targetClass = targetClass;
        this.targetMethod = (!Proxy.isProxyClass(targetClass) ?
                AopUtils.getMostSpecificMethod(method, targetClass) : this.method);
        this.methodKey = new AnnotatedElementKey(this.targetMethod, targetClass);
    }
}
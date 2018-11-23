package cn.com.likly.finalframework.cache;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0
 * @date 2018-11-23 15:46:07
 * @since 1.0
 */
public interface CacheOperationInvocationContext<O extends CacheOperation> {
    O operation();

    Object target();

    Method method();

    Object[] args();

    Class<?> returnType();

    Type genericReturnType();

    Object generateKey(Object result);

    Object generateField(Object result);

    boolean isConditionPassing(Object result);

    Object generateExpired(Object result);
}

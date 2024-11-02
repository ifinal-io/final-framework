package org.ifinalframework.context.beans.factory.support;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author iimik
 * @version 1.2.4
 **/
public interface BeanMethodInvoker {
    /**
     * invoke method
     */
    default Object invoke(@NonNull Object bean, @NonNull String methodName, @Nullable Object[] args) {
        return invoke(bean, methodName, null, args);
    }

    /**
     * invoke method
     */
    @Nullable
    Object invoke(@NonNull String beanName, @NonNull String methodName, @Nullable Class<?>[] parameterTypes, @Nullable Object[] args);

    /**
     * invoke method
     */
    @Nullable
    Object invoke(@NonNull Class<?> beanType, @NonNull String methodName, @Nullable Class<?>[] parameterTypes, @Nullable Object[] args);

    /**
     * invoke method
     */
    @Nullable
    Object invoke(@NonNull Object bean, @NonNull String methodName, @Nullable Class<?>[] parameterTypes, @Nullable Object[] args);

}

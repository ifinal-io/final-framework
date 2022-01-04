package org.ifinalframework.context.beans.factory.support;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.2.4
 **/
public interface BeanMethodInvoker {

    default Object invoke(@NonNull Object bean, @NonNull String methodName, @Nullable Object[] args) {
        return invoke(bean, methodName, null, args);
    }

    @Nullable
    Object invoke(@NonNull String beanName,@NonNull String methodName,@Nullable Class<?>[] parameterTypes,@Nullable Object[] args);

    @Nullable
    Object invoke(@NonNull Class<?> beanType,@NonNull String methodName,@Nullable Class<?>[] parameterTypes,@Nullable Object[] args);

    @Nullable
    Object invoke(@NonNull Object bean, @NonNull String methodName, @Nullable Class<?>[] parameterTypes, @Nullable Object[] args);

}

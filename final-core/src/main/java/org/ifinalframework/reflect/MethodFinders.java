package org.ifinalframework.reflect;


import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @author likly
 * @version 1.2.4
 **/
public class MethodFinders implements MethodFinder {

    private final List<MethodFinder> methodFinders;

    public MethodFinders(List<MethodFinder> methodFinders) {
        this.methodFinders = methodFinders;
    }



    @Override
    @Nullable
    public Method find(@NonNull Class<?> targetClass, @NonNull String methodName,
                       @Nullable Class<?>[] parameterTypes,@Nullable Object[] arguments) {

        if(Objects.nonNull(parameterTypes) && Objects.nonNull(arguments) && !Objects.equals(parameterTypes.length,arguments.length)){
            throw new IllegalArgumentException(String.format("%s.%s parameterTypes and arguments length not matched.", targetClass.getCanonicalName(),methodName));
        }

        return methodFinders.stream()
                .map(it -> it.find(targetClass, methodName, parameterTypes, arguments))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

    }


}

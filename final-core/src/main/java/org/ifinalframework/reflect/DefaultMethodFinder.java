package org.ifinalframework.reflect;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.MethodInvoker;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.2.4
 **/
public class DefaultMethodFinder extends MethodFinders {


    public DefaultMethodFinder() {
        super(Arrays.asList(
                new ParameterTypesMethodFinder(),
                new NameMethodFinder(),
                new ArgumentsMethodFinder()
        ));
    }

    private static class ParameterTypesMethodFinder implements MethodFinder {

        @Override
        @Nullable
        public Method find(@NonNull Class<?> targetClass, @NonNull String methodName,
                           @Nullable Class<?>[] parameterTypes, @Nullable Object[] arguments) {

            if (Objects.isNull(parameterTypes)) {
                return ReflectionUtils.findMethod(targetClass, methodName);
            }

            return ReflectionUtils.findMethod(targetClass, methodName, parameterTypes);
        }
    }

    private static class ArgumentsMethodFinder implements MethodFinder {

        @Override
        @Nullable
        public Method find(@NonNull Class<?> targetClass, @NonNull String methodName,
                           @Nullable Class<?>[] parameterTypes, @Nullable Object[] arguments) {

            if (Objects.isNull(arguments)) {
                return null;
            }
            Method[] candidates = ReflectionUtils.getAllDeclaredMethods(targetClass);
            int argCount = arguments.length;

            if(isAllString(arguments)){
                List<Method> matchingMethods = Arrays.stream(candidates)
                        .filter(it -> it.getName().equals(methodName) && it.getParameterCount() == argCount)
                        .collect(Collectors.toList());

                if(matchingMethods.size() == 1){
                    return matchingMethods.get(0);
                }

                if(matchingMethods.size() > 1){
                    throw new IllegalStateException("Found not only one method of " + methodName + " in class " + targetClass.getCanonicalName());
                }

                return null;
            }else {
                int minTypeDiffWeight = Integer.MAX_VALUE;
                Method matchingMethod = null;

                for (Method candidate : candidates) {
                    if (candidate.getName().equals(methodName)) {
                        if (candidate.getParameterCount() == argCount) {
                            Class<?>[] paramTypes = candidate.getParameterTypes();
                            int typeDiffWeight = MethodInvoker.getTypeDifferenceWeight(paramTypes, arguments);
                            if (typeDiffWeight < minTypeDiffWeight) {
                                minTypeDiffWeight = typeDiffWeight;
                                matchingMethod = candidate;
                            }
                        }
                    }
                }

                return matchingMethod;
            }

        }

        private boolean isAllString(Object[] arguments){
            for (Object argument : arguments) {
                if(!(argument instanceof String)){
                    return false;
                }
            }
            return true;
        }
    }

    private static class NameMethodFinder implements MethodFinder {

        @Override
        @Nullable
        public Method find(@NonNull Class<?> targetClass, @NonNull String methodName,
                           @Nullable Class<?>[] parameterTypes, @Nullable Object[] arguments) {

            if (Objects.nonNull(arguments)) {
                return null;
            }

            Method[] candidates = ReflectionUtils.getAllDeclaredMethods(targetClass);

            List<Method> matchingMethods = Arrays.stream(candidates)
                    .filter(it -> it.getName().equals(methodName))
                    .collect(Collectors.toList());

            if(matchingMethods.size() == 1){
                return matchingMethods.get(0);
            }

            if(matchingMethods.size() > 1){
                throw new IllegalStateException("Found not only one method of " + methodName + " in class " + targetClass.getCanonicalName());
            }

            return null;
        }
    }


}

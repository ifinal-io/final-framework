package org.ifinal.finalframework.context.resource;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;
import org.ifinal.finalframework.annotation.resource.ResourceValue;
import org.springframework.core.annotation.AnnotatedElementUtils;

/**
 * ResourceValueUtils.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ResourceValueUtils {

    private ResourceValueUtils() {
    }

    public static Collection<ResourceValueHolder> findAllResourceValueHolders(final Object target, final Class<?> clazz) {

        final Collection<ResourceValueHolder> holders = new LinkedList<>();

        if (Objects.isNull(clazz)) {
            return holders;
        }

        ResourceValue resourceValue = AnnotatedElementUtils.findMergedAnnotation(clazz, ResourceValue.class);

        if (Objects.isNull(resourceValue)) {
            return holders;
        }

        if (Objects.equals(Object.class, clazz)) {
            return holders;
        }

        for (final Field field : clazz.getDeclaredFields()) {

            ResourceValueHolder valueHolder = processElement(target, field, resourceValue);

            if (Objects.nonNull(valueHolder)) {
                holders.add(valueHolder);
            }

        }

        for (final Method method : clazz.getDeclaredMethods()) {
            ResourceValueHolder valueHolder = processElement(target, method, resourceValue);

            if (Objects.nonNull(valueHolder)) {
                holders.add(valueHolder);
            }
        }

        holders.addAll(findAllResourceValueHolders(target, clazz.getSuperclass()));

        for (final Class<?> item : clazz.getInterfaces()) {
            holders.addAll(findAllResourceValueHolders(target, item));
        }

        return holders;

    }

    private static ResourceValueHolder processElement(final Object target, final AnnotatedElement element,
        final ResourceValue resourceValue) {
        ResourceValue annotation = AnnotatedElementUtils.findMergedAnnotation(element, ResourceValue.class);

        if (Objects.isNull(annotation)) {
            return null;
        }

        return new ResourceValueHolder(key(resourceValue, annotation), target, element);

    }

    private static String key(final ResourceValue... resourceValues) {
        return Arrays.stream(resourceValues)
            .map(ResourceValue::value)
            .filter(it -> !it.isEmpty())
            .collect(Collectors.joining("."));
    }

}

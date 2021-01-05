package org.ifinal.finalframework.context.resource;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import lombok.Getter;
import org.springframework.util.ReflectionUtils;

/**
 * ResourceValueHolder.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public final class ResourceValueHolder {

    private final String key;

    private final Object target;

    private final AnnotatedElement element;

    private final ResourceValueType type;

    private final Type valueType;

    private Object value;

    public ResourceValueHolder(final String key, final Object target, final AnnotatedElement element) {
        this.key = key;
        this.target = target;
        this.element = element;

        if (element instanceof Field) {
            this.type = ResourceValueType.FIELD;
            valueType = ((Field) element).getType();
            ReflectionUtils.makeAccessible((Field) element);
        } else if (element instanceof Method) {
            this.type = ResourceValueType.METHOD;
            ReflectionUtils.makeAccessible((Method) element);
            valueType = ((Method) element).getGenericParameterTypes()[0];
        } else {
            throw new IllegalArgumentException("result value only support filed or method. not support the type of " + element);
        }

    }

    public void setValue(final Object value) {
        switch (type) {
            case FIELD:
                try {
                    ((Field) element).set(target, value);
                } catch (IllegalAccessException e) {
                    throw new IllegalArgumentException(e);
                }
                break;
            case METHOD:
                try {
                    ((Method) element).invoke(target, value);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new IllegalArgumentException(e);
                }
                break;
            default:
                throw new IllegalArgumentException("");
        }

        this.value = value;
    }

}

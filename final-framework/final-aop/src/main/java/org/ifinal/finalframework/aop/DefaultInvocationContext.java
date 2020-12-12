package org.ifinal.finalframework.aop;


import com.fasterxml.jackson.annotation.JsonView;
import org.ifinal.finalframework.context.expression.MethodMetadata;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class DefaultInvocationContext implements InvocationContext {
    private final MethodMetadata metadata;
    private final Object target;
    private final Object[] args;
    private final Class<?> view;
    private final Map<String, Object> attributes = new HashMap<>();

    public DefaultInvocationContext(final MethodMetadata metadata, final Object target, final Object[] args) {

        this.metadata = metadata;
        this.target = target;
        this.args = extractArgs(metadata.getMethod(), args);
        this.view = extractView(metadata.getMethod());
    }

    @Override
    public MethodMetadata metadata() {
        return metadata;
    }

    @Override
    public Object target() {
        return target;
    }

    @Override
    public Object[] args() {
        return args;
    }

    @Override
    public Class<?> view() {
        return view;
    }

    @Override
    public Map<String, Object> attributes() {
        return attributes;
    }

    @Override
    public void addAttribute(final String name, final Object value) {

        attributes.put(name, value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(final String name) {

        return (T) attributes.get(name);
    }

    private Object[] extractArgs(final Method method, final Object[] args) {

        if (!method.isVarArgs()) {
            return args;
        }
        Object[] varArgs = ObjectUtils.toObjectArray(args[args.length - 1]);
        Object[] combinedArgs = new Object[args.length - 1 + varArgs.length];
        System.arraycopy(args, 0, combinedArgs, 0, args.length - 1);
        System.arraycopy(varArgs, 0, combinedArgs, args.length - 1, varArgs.length);
        return combinedArgs;
    }


    private Class<?> extractView(final Method method) {

        final JsonView jsonView = AnnotationUtils.findAnnotation(method, JsonView.class);
        if (jsonView == null) return null;
        Class<?>[] classes = jsonView.value();
        if (classes.length != 1) {
            throw new IllegalArgumentException(
                    "@JsonView only supported for cache advice with exactly 1 class argument: " + method.getDeclaringClass().getCanonicalName() + "#" + method.getName());
        }
        return classes[0];
    }
}

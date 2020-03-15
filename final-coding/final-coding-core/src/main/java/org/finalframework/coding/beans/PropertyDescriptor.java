package org.finalframework.coding.beans;


import org.springframework.data.util.Lazy;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import java.util.Optional;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-15 00:59:25
 * @since 1.0
 */
public class PropertyDescriptor {

    private final Bean bean;
    private final Optional<VariableElement> field;
    private final Optional<ExecutableElement> setter;
    private final Optional<ExecutableElement> getter;
    private final Lazy<String> toString;

    public PropertyDescriptor(Bean bean, Optional<VariableElement> field,
                              Optional<ExecutableElement> setter,
                              Optional<ExecutableElement> getter) {
        this.bean = bean;
        this.field = field;
        this.setter = setter;
        this.getter = getter;

        this.toString = Lazy.of(() -> {
            StringBuilder sb = new StringBuilder();
            sb.append("field=").append(field.map(e -> e.getSimpleName().toString()).orElse(""))
                    .append(",setter=").append(setter.map(e -> e.getSimpleName().toString()).orElse(""))
                    .append(",getter=").append(getter.map(e -> e.getSimpleName().toString()).orElse(""));
            return sb.toString();
        });
    }

    public Bean getBean() {
        return bean;
    }

    public VariableElement getField() {
        return field.get();
    }

    public ExecutableElement getGetter() {
        return getter.get();
    }

    public ExecutableElement getSetter() {
        return setter.get();
    }

    @Override
    public String toString() {
        return toString.get();
    }
}


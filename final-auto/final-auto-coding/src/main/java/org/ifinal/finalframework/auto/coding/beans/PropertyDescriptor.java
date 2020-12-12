package org.ifinal.finalframework.auto.coding.beans;


import org.springframework.data.util.Lazy;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import java.util.Optional;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class PropertyDescriptor {

    private final Bean bean;

    private final String name;

    private final Optional<VariableElement> field;

    private final Optional<ExecutableElement> setter;

    private final Optional<ExecutableElement> getter;

    private final Lazy<String> toString;

    public PropertyDescriptor(final Bean bean, final String name, final Optional<VariableElement> field,
                              final Optional<ExecutableElement> setter,
                              final Optional<ExecutableElement> getter) {

        this.bean = bean;
        this.name = name;
        this.field = field;
        this.setter = setter;
        this.getter = getter;

        this.toString = Lazy.of(() -> {
            StringBuilder sb = new StringBuilder();
            sb.append(name).append(":{field=").append(field.map(e -> e.getSimpleName().toString()).orElse(""))
                    .append(",setter=").append(setter.map(e -> e.getSimpleName().toString()).orElse(""))
                    .append(",getter=").append(getter.map(e -> e.getSimpleName().toString()).orElse(""))
                    .append("}");
            return sb.toString();
        });
    }

    public Bean getBean() {
        return bean;
    }

    public String getName() {
        return name;
    }

    public Optional<VariableElement> getField() {
        return field;
    }

    public Optional<ExecutableElement> getSetter() {
        return setter;
    }

    public Optional<ExecutableElement> getGetter() {
        return getter;
    }

    @Override
    public String toString() {
        return toString.get();
    }
}


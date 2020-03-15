package org.finalframework.coding.beans;


import org.finalframework.core.filter.FilterVisitor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-15 16:13:19
 * @since 1.0
 */
public class SetterAndGetterFilter implements FilterVisitor<ExecutableElement, TypeMirror> {
    private final ProcessingEnvironment env;

    public SetterAndGetterFilter(ProcessingEnvironment env) {
        this.env = env;
    }

    @Override
    public boolean matches(ExecutableElement method, TypeMirror parameterTypeOrReturnType) {
        if (method.getKind() != ElementKind.METHOD) return false;
        String name = method.getSimpleName().toString();
        List<? extends VariableElement> parameters = method.getParameters();
        return isSetter(name, parameters) || isGetter(name, parameters);
    }

    public boolean isSetter(ExecutableElement method) {
        String name = method.getSimpleName().toString();
        List<? extends VariableElement> parameters = method.getParameters();
        return isSetter(name, parameters);
    }

    public boolean isGetter(ExecutableElement method) {
        String name = method.getSimpleName().toString();
        List<? extends VariableElement> parameters = method.getParameters();
        return isGetter(name, parameters);
    }

    private boolean isSetter(String name, List<? extends VariableElement> parameters) {
        return parameters.size() == 1 && name.startsWith(Bean.SET_PREFIX);
    }

    private boolean isGetter(String name, List<? extends VariableElement> parameters) {
        return parameters.size() == 0 && (name.startsWith(Bean.GET_PREFIX) || name.startsWith(Bean.IS_PREFIX));
    }


}


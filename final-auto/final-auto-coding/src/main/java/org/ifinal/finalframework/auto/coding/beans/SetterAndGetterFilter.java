package org.ifinal.finalframework.auto.coding.beans;


import lombok.extern.slf4j.Slf4j;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
public class SetterAndGetterFilter {

    public SetterAndGetterFilter(final ProcessingEnvironment env) {

    }

    public boolean matches(final ExecutableElement method, final TypeMirror parameterTypeOrReturnType) {

        if (method.getKind() != ElementKind.METHOD) return false;

        if (Objects.nonNull(parameterTypeOrReturnType)) {
            // check
            logger.info("try to check method parameterTypeOrReturnType");
        }


        String name = method.getSimpleName().toString();
        List<? extends VariableElement> parameters = method.getParameters();
        return isSetter(name, parameters) || isGetter(name, parameters);
    }

    public boolean isSetter(final ExecutableElement method) {

        String name = method.getSimpleName().toString();
        List<? extends VariableElement> parameters = method.getParameters();
        return isSetter(name, parameters);
    }

    public boolean isGetter(final ExecutableElement method) {

        String name = method.getSimpleName().toString();
        List<? extends VariableElement> parameters = method.getParameters();
        return isGetter(name, parameters);
    }

    private boolean isSetter(final String name, final List<? extends VariableElement> parameters) {

        return parameters.size() == 1 && name.startsWith(Bean.SET_PREFIX);
    }

    private boolean isGetter(final String name, final List<? extends VariableElement> parameters) {

        return parameters.isEmpty() && (name.startsWith(Bean.GET_PREFIX) || name.startsWith(Bean.IS_PREFIX));
    }


}


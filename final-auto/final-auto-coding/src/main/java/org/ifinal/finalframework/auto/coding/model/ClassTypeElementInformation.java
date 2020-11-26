package org.ifinal.finalframework.auto.coding.model;


import org.ifinal.finalframework.auto.coding.utils.CodeUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class ClassTypeElementInformation implements TypeElementInformation<TypeElement> {
    private final TypeElement element;

    public ClassTypeElementInformation(TypeElement element) {
        this.element = element;
    }

    @Override
    public Element getElement() {
        return this.element;
    }

    public List<VariableElement> getFields() {
        return CodeUtils.fields(element);
    }

    public List<ExecutableElement> getMethods() {
        return CodeUtils.methods(element);
    }
}


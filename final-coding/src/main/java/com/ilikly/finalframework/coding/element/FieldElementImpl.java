package com.ilikly.finalframework.coding.element;

import javax.lang.model.element.VariableElement;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-12 19:25:31
 * @since 1.0
 */
public class FieldElementImpl implements FieldElement {
    private final VariableElement variableElement;

    public FieldElementImpl(VariableElement variableElement) {
        this.variableElement = variableElement;
    }

    @Override
    public String getName() {
        return variableElement.getSimpleName().toString();
    }

}

package org.finalframework.coding.entity;


import org.finalframework.coding.entity.filter.PropertyExecutableElementFilter;
import org.finalframework.coding.entity.filter.PropertyVariableElementFilter;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.SimpleElementVisitor8;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-14 21:08:51
 * @see PropertyVariableElementFilter
 * @since 1.0
 */
public class PropertyElementVisitor extends SimpleElementVisitor8<Property, TypeElement> {

    private PropertyVariableElementFilter propertyVariableElementFilter = new PropertyVariableElementFilter();
    private PropertyExecutableElementFilter propertyExecutableElementFilter = new PropertyExecutableElementFilter();

    @Override
    public Property visitVariable(VariableElement e, TypeElement entity) {

        if (!propertyVariableElementFilter.matches(e)) return null;
        // this element must be a field.
        return null;
    }


    @Override
    public Property visitExecutable(ExecutableElement e, TypeElement typeElement) {
        return null;
    }
}


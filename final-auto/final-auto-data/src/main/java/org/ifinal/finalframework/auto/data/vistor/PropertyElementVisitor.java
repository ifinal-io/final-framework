package org.ifinal.finalframework.auto.data.vistor;


import org.ifinal.finalframework.auto.data.Property;
import org.ifinal.finalframework.auto.data.filter.PropertyExecutableElementFilter;
import org.ifinal.finalframework.auto.data.filter.PropertyVariableElementFilter;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.SimpleElementVisitor8;

/**
 * @author likly
 * @version 1.0.0
 * @see PropertyVariableElementFilter
 * @since 1.0.0
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


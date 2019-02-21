package com.ilikly.finalframework.coding.element;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-12 18:59:38
 * @since 1.0
 */
public interface ClassElement extends Element {

    ClassElement getSuperClassElement();

    Collection<FieldElement> getFieldElements();

    Collection<MethodElement> getMethodElements();
}

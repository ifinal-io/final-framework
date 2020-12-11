package org.ifinal.finalframework.auto.coding;


import javax.lang.model.element.Element;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IElementVisitor<R, P> extends ElementVisitor<R, P> {
    @Override
    default R visit(Element e, P p) {
        return null;
    }

    @Override
    default R visit(Element e) {
        return null;
    }

    @Override
    default R visitPackage(PackageElement e, P p) {
        return null;
    }

    @Override
    default R visitType(TypeElement e, P p) {
        return null;
    }

    @Override
    default R visitVariable(VariableElement e, P p) {
        return null;
    }

    @Override
    default R visitExecutable(ExecutableElement e, P p) {
        return null;
    }

    @Override
    default R visitTypeParameter(TypeParameterElement e, P p) {
        return null;
    }

    @Override
    default R visitUnknown(Element e, P p) {
        return null;
    }
}


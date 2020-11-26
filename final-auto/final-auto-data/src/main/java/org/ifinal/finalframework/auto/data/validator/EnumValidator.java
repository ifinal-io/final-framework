package org.ifinal.finalframework.auto.data.validator;

import org.ifinal.finalframework.util.function.Filter;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.SimpleElementVisitor8;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class EnumValidator extends SimpleElementVisitor8<Void, Void> implements
        Filter<TypeElement> {

    private final ProcessingEnvironment processingEnvironment;
    private final Class<?> enumInterface;

    public EnumValidator(ProcessingEnvironment processingEnvironment, Class<?> enumInterface) {
        this.processingEnvironment = processingEnvironment;
        this.enumInterface = enumInterface;
    }

    @Override
    public Void visitType(TypeElement e, Void aVoid) {
        if (matches(e)) {
            validate(e, enumInterface);
        }
        return super.visitType(e, aVoid);
    }

    @Override
    public boolean matches(TypeElement typeElement) {
        return ElementKind.ENUM == typeElement.getKind();
    }

    public Void validate(TypeElement typeElement, Class<?> enumInterface) {

        if (!isAssignable(typeElement, enumInterface)) {
            processingEnvironment.getMessager()
                    .printMessage(Kind.ERROR,
                            "the enum type of " + typeElement.getQualifiedName().toString() + " must be implements the interface of " + enumInterface.getCanonicalName());
        }
        return null;
    }

    private boolean isAssignable(TypeElement element, Class<?> clazz) {
        Types typeUtils = processingEnvironment.getTypeUtils();
        TypeElement typeElement = processingEnvironment.getElementUtils().getTypeElement(clazz.getCanonicalName());
        return typeUtils.isSubtype(typeUtils.erasure(element.asType()), typeUtils.erasure(typeElement.asType()));
    }
}

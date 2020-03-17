package org.finalframework.coding.entity.validator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.SimpleElementVisitor8;
import javax.tools.Diagnostic.Kind;
import org.finalframework.core.filter.Filter;
import org.finalframework.core.validator.ValidatorVisitor;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-17 21:39:17
 * @since 1.0
 */
public class EnumValidator extends SimpleElementVisitor8<Void, Void> implements
    ValidatorVisitor<Void, TypeElement, Class<?>>,
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

    @Override
    public Void validate(TypeElement typeElement, Class<?> enumInterface) {
        boolean assignable = processingEnvironment.getTypeUtils().isAssignable(typeElement.asType(),
            processingEnvironment.getElementUtils().getTypeElement(enumInterface.getCanonicalName()).asType());

        if (!assignable) {
            processingEnvironment.getMessager()
                .printMessage(Kind.ERROR,
                    "the enum type must be implements the interface of " + enumInterface.getCanonicalName());
        }
        return null;
    }
}

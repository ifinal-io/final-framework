package org.ifinal.finalframework.auto.data.validator;

import org.ifinal.finalframework.util.function.FilterVisitor;
import org.springframework.lang.NonNull;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.SimpleElementVisitor8;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class EntityValidator extends SimpleElementVisitor8<Void, Void>
        implements
        FilterVisitor<TypeElement, Class<?>> {


    private static final List<Modifier> modifiers = Arrays.asList(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL);

    private static final String SERIAL_VERSION_UID_NAME = "serialVersionUID";


    private final ProcessingEnvironment processingEnv;

    private final Class<?> entityInterface;


    public EntityValidator(final ProcessingEnvironment processingEnv, final Class<?> entityInterface) {

        this.processingEnv = processingEnv;
        this.entityInterface = entityInterface;
    }

    @Override
    public Void visitType(final TypeElement e, final Void param) {

        if (matches(e, entityInterface)) {
            validate(e);
        }
        return super.visitType(e, param);
    }

    @Override
    public boolean matches(final @NonNull TypeElement typeElement, final Class<?> param) {

        return isAssignable(typeElement, entityInterface);
    }

    public void validate(final TypeElement data) {

        if (!isAssignable(data, Serializable.class)) {
            error("The entity of " + data.getQualifiedName().toString() + " must be implements the interface of "
                    + Serializable.class.getSimpleName());
        }

        Optional<VariableElement> optional = ElementFilter.fieldsIn(data.getEnclosedElements())
                .stream()
                .filter(it -> it.getModifiers().containsAll(modifiers)
                        && it.getSimpleName().toString().equals(SERIAL_VERSION_UID_NAME)
                        && it.asType().getKind() == TypeKind.LONG)
                .findFirst();

        if (!optional.isPresent()) {
            error("The entity of " + data.getQualifiedName().toString() + " must be have a long type field named "
                    + SERIAL_VERSION_UID_NAME
                    + " and modified with 'private','static' and 'final'.");
        }

    }

    private boolean isAssignable(final TypeElement element, final Class<?> clazz) {

        Types typeUtils = processingEnv.getTypeUtils();
        TypeElement typeElement = processingEnv.getElementUtils().getTypeElement(clazz.getCanonicalName());
        return typeUtils.isSubtype(typeUtils.erasure(element.asType()), typeUtils.erasure(typeElement.asType()));
    }

    private void error(final String message) {

        processingEnv.getMessager().printMessage(Kind.ERROR, message);
    }

}

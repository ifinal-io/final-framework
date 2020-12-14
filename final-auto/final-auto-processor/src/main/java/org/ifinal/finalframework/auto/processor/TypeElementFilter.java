package org.ifinal.finalframework.auto.processor;

import java.util.Objects;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import org.ifinal.finalframework.util.function.Filter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class TypeElementFilter implements Filter<TypeElement> {

    private final Types types;

    private final Messager messager;

    private final TypeElement entityTypeElement;

    private final TypeElement transientAnnotationTypeElement;

    public TypeElementFilter(final @NonNull ProcessingEnvironment processingEnvironment,
        final @NonNull TypeElement entityTypeElement,
        final @Nullable TypeElement transientAnnotationTypeElement) {

        Objects.requireNonNull(entityTypeElement, "typeElement can not be null!");

        this.types = processingEnvironment.getTypeUtils();
        this.messager = processingEnvironment.getMessager();
        this.entityTypeElement = entityTypeElement;
        this.transientAnnotationTypeElement = transientAnnotationTypeElement;
    }

    @Override
    public boolean matches(final TypeElement typeElement) {
        //忽略被注解不解析的类
        if (isAnnotated(typeElement, transientAnnotationTypeElement)) {
            return false;
        }
        messager
            .printMessage(Diagnostic.Kind.NOTE, String.format("[INFO] [TypeElementFilter] filter typeElement: %s",
                typeElement.getQualifiedName().toString()));
        final boolean subtype = types
            .isSubtype(types.erasure(typeElement.asType()), types.erasure(entityTypeElement.asType()));
        if (subtype) {
            final String msg = "[INFO] [EntityFilter] find entity : " + typeElement.getQualifiedName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, msg);
        }
        return subtype;
    }

    private boolean isAnnotated(final @NonNull TypeElement element, final @Nullable TypeElement annotationTypeElement) {

        if (Objects.isNull(annotationTypeElement)) {
            return false;
        }

        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            if (types.isSameType(annotationMirror.getAnnotationType(), annotationTypeElement.asType())) {
                return true;
            }
        }

        return false;

    }

}


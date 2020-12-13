package org.ifinal.finalframework.auto.coding.utils;

import java.lang.annotation.Annotation;
import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import org.springframework.lang.NonNull;

/**
 * @author sli
 * @version 1.0.0
 * @since 1.0.0
 */
public class Annotations {

    @SuppressWarnings("unused")
    private final ProcessingEnvironment processEnv;

    public Annotations(final ProcessingEnvironment processEnv) {

        this.processEnv = processEnv;
    }

    public static boolean isAnnotationPresent(final @NonNull Element element,
        final @NonNull Class<? extends Annotation> ann) {

        if (element.getAnnotation(ann) != null) {
            return true;
        }
        List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
        for (AnnotationMirror annotationMirror : annotationMirrors) {
            DeclaredType annotationType = annotationMirror.getAnnotationType();
            if (annotationType.asElement().getAnnotation(ann) != null) {
                return true;
            }
        }

        return false;

    }

}

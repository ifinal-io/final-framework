package org.ifinal.finalframework.auto.model;


import org.springframework.lang.NonNull;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0.0
 * @see AnnotationMirror
 * @since 1.0.0
 */
public final class AnnotationMirrors {
    private AnnotationMirrors() {
    }

    public static Map<String, AnnotationValue> getAnnotationValues(final @NonNull AnnotationMirror mirror) {

        return mirror.getElementValues()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> entry.getKey().getSimpleName().toString(), Map.Entry::getValue));
    }

}


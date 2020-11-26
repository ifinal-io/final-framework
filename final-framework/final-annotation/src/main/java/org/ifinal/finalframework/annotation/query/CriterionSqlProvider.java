package org.ifinal.finalframework.annotation.query;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CriterionSqlProvider {
    String provide(@NonNull AnnotationAttributes annotationAttributes, @NonNull Metadata metadata);
}

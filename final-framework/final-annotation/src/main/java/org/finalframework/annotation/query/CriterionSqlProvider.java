package org.finalframework.annotation.query;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-20 09:25:40
 * @since 1.0
 */
public interface CriterionSqlProvider {
    String provide(@NonNull AnnotationAttributes annotationAttributes, @NonNull Metadata metadata);
}

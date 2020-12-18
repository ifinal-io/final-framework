package org.ifinal.finalframework.annotation.query;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CriterionSqlProvider {

    void function(@NonNull AnnotationAttributes annotation, @NonNull CriterionAttributes metadata);

    String order(@NonNull AnnotationAttributes order, @NonNull CriterionAttributes metadata);

    String provide(@NonNull AnnotationAttributes criterion, @NonNull CriterionAttributes metadata);

}

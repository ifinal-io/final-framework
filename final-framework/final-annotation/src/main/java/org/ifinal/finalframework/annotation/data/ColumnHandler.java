package org.ifinal.finalframework.annotation.data;

import org.ifinal.finalframework.annotation.query.CriterionAttributes;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ColumnHandler {

    String handleWriter(@NonNull Column column, @NonNull CriterionAttributes metadata);

    String handleReader(@NonNull Column column, @NonNull CriterionAttributes metadata);

}

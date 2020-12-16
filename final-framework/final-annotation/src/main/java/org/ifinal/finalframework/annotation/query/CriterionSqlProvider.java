package org.ifinal.finalframework.annotation.query;

import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CriterionSqlProvider {

    String provide(@NonNull String[] criterion, @NonNull CriterionAttributes metadata);

}

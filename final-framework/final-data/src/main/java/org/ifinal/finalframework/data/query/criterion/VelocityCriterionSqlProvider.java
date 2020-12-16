package org.ifinal.finalframework.data.query.criterion;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.ifinal.finalframework.annotation.query.CriterionAttributes;
import org.ifinal.finalframework.annotation.query.CriterionSqlProvider;
import org.ifinal.finalframework.data.util.Velocities;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class VelocityCriterionSqlProvider implements CriterionSqlProvider {

    private static final String DEFAULT_DELIMITER = " ";

    @Override
    public void function(@NonNull final AnnotationAttributes function,
        @NonNull final CriterionAttributes metadata) {

        String value = Arrays.stream(function.getStringArray("value"))
            .map(String::trim)
            .collect(Collectors.joining(DEFAULT_DELIMITER));

        final String column = Velocities.getValue(value, metadata);

        metadata.put("column", column);

    }

    @Override
    public String provide(@NonNull final AnnotationAttributes criterion, @NonNull final CriterionAttributes metadata) {
        final String value = Arrays.stream(criterion.getStringArray("value"))
            .map(String::trim)
            .collect(Collectors.joining(DEFAULT_DELIMITER));
        return Velocities.getValue(value, metadata);
    }

}


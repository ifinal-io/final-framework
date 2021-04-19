package org.ifinal.finalframework.data.query.criterion;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;

import org.ifinal.finalframework.query.CriterionAttributes;
import org.ifinal.finalframework.query.annotation.CriterionSqlProvider;
import org.ifinal.finalframework.velocity.Velocities;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class VelocityCriterionSqlProvider implements CriterionSqlProvider {

    private static final String ATTRIBUTE_VALUE_KEY = "value";

    private static final String DEFAULT_DELIMITER = " ";

    @Override
    public void function(@NonNull final AnnotationAttributes function,
        @NonNull final CriterionAttributes metadata) {

        String value = Arrays.stream(function.getStringArray(ATTRIBUTE_VALUE_KEY))
            .map(String::trim)
            .collect(Collectors.joining(DEFAULT_DELIMITER));

        final String column = Velocities.getValue(value, metadata);

        metadata.put("column", column);

    }

    @Override
    public String order(@NonNull final AnnotationAttributes annotation, @NonNull final CriterionAttributes metadata) {
        String value = Arrays.stream(annotation.getStringArray(ATTRIBUTE_VALUE_KEY))
            .map(String::trim)
            .collect(Collectors.joining(DEFAULT_DELIMITER));
        final String order = Velocities.getValue(value, metadata);
        return order;
    }

    @Override
    public String provide(@NonNull final AnnotationAttributes criterion, @NonNull final CriterionAttributes metadata) {
        final String value = Arrays.stream(criterion.getStringArray(ATTRIBUTE_VALUE_KEY))
            .map(String::trim)
            .collect(Collectors.joining(DEFAULT_DELIMITER));
        return Velocities.getValue(value, metadata);
    }

}


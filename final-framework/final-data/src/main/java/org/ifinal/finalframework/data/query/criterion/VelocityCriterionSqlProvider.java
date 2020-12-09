package org.ifinal.finalframework.data.query.criterion;


import org.ifinal.finalframework.data.util.Velocities;
import org.ifinal.finalframework.query.annotation.CriterionSqlProvider;
import org.ifinal.finalframework.query.annotation.Metadata;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class VelocityCriterionSqlProvider implements CriterionSqlProvider {

    @Override
    public String provide(@NonNull AnnotationAttributes annotationAttributes, @NonNull Metadata metadata) {
        final String value = Arrays.stream(annotationAttributes.getStringArray("value")).map(String::trim).collect(Collectors.joining());
        return Velocities.getValue(value, metadata);
    }
}


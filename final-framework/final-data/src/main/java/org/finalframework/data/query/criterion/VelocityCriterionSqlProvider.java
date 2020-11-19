package org.finalframework.data.query.criterion;


import org.finalframework.annotation.query.CriterionSqlProvider;
import org.finalframework.annotation.query.Metadata;
import org.finalframework.data.util.Velocities;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-20 12:57:02
 * @since 1.0
 */
public class VelocityCriterionSqlProvider implements CriterionSqlProvider {

    @Override
    public String provide(@NonNull AnnotationAttributes annotationAttributes, @NonNull Metadata metadata) {
        final String value = Arrays.stream(annotationAttributes.getStringArray("value")).map(String::trim).collect(Collectors.joining());
        return Velocities.getValue(value, metadata);
    }
}


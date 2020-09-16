package org.finalframework.data.query.criterion;


import org.finalframework.annotation.query.Criterion;
import org.finalframework.annotation.query.CriterionHandler;
import org.finalframework.annotation.query.Metadata;
import org.finalframework.data.util.Velocities;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-20 12:57:02
 * @since 1.0
 */
public class VelocityCriterionHandler implements CriterionHandler {
    @Override
    public String handle(Criterion criterion, Metadata metadata) {
        final String value = Arrays.stream(criterion.value()).map(String::trim).collect(Collectors.joining());
        return Velocities.getValue(value, metadata);
    }
}


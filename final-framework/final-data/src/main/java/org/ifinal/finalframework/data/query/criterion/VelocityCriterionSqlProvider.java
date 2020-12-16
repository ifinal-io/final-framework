package org.ifinal.finalframework.data.query.criterion;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.ifinal.finalframework.annotation.query.CriterionAttributes;
import org.ifinal.finalframework.annotation.query.CriterionSqlProvider;
import org.ifinal.finalframework.data.util.Velocities;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class VelocityCriterionSqlProvider implements CriterionSqlProvider {

    private static final String DEFAULT_DELIMITER = " ";

    @Override
    public String provide(final @NonNull String[] criterion, final @NonNull CriterionAttributes metadata) {
        final String value = Arrays.stream(criterion).map(String::trim).collect(Collectors.joining(DEFAULT_DELIMITER));
        return Velocities.getValue(value, metadata);
    }

}


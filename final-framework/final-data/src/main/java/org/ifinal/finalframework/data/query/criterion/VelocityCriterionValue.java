package org.ifinal.finalframework.data.query.criterion;

import org.ifinal.finalframework.data.util.Velocities;
import org.ifinal.finalframework.query.CriterionAttributes;

import lombok.RequiredArgsConstructor;

/**
 * VelocityCriterion.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class VelocityCriterionValue {

    private final String expression;

    public VelocityCriterionValue(final String... expression) {
        this.expression = String.join(" ",expression);
    }


    public String value(CriterionAttributes criterion) {
        return Velocities.getValue(expression, criterion);
    }


}

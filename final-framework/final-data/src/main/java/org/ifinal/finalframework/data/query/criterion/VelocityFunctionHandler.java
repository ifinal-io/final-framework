package org.ifinal.finalframework.data.query.criterion;


import org.ifinal.finalframework.annotation.query.Function;
import org.ifinal.finalframework.annotation.query.Metadata;
import org.ifinal.finalframework.data.util.Velocities;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class VelocityFunctionHandler implements Function.FunctionHandler {
    @Override
    public String handle(final Function function, final Metadata metadata) {

        return Velocities.getValue(function.value(), metadata);
    }
}


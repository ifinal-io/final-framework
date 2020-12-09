package org.ifinal.finalframework.data.query.criterion;


import org.ifinal.finalframework.data.util.Velocities;
import org.ifinal.finalframework.query.annotation.Function;
import org.ifinal.finalframework.query.annotation.Metadata;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class VelocityFunctionHandler implements Function.FunctionHandler {
    @Override
    public String handle(Function function, Metadata metadata) {
        return Velocities.getValue(function.value(), metadata);
    }
}


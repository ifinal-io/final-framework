package org.finalframework.data.query.criterion;


import org.finalframework.annotation.query.Function;
import org.finalframework.annotation.query.Metadata;
import org.finalframework.data.util.Velocities;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-20 13:32:25
 * @since 1.0
 */
public class VelocityFunctionHandler implements Function.FunctionHandler {
    @Override
    public String handle(Function function, Metadata metadata) {
        return Velocities.getValue(function.value(), metadata);
    }
}


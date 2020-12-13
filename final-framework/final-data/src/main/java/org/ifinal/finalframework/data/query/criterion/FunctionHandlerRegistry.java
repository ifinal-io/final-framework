package org.ifinal.finalframework.data.query.criterion;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.ifinal.finalframework.annotation.query.Function;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class FunctionHandlerRegistry {

    private static final FunctionHandlerRegistry INSTANCE = new FunctionHandlerRegistry();

    private final Map<Class<? extends Function.FunctionHandler>, Function.FunctionHandler> handlers
        = new ConcurrentHashMap<>(8);

    private FunctionHandlerRegistry() {
        registry(Function.FunctionHandler.class, new VelocityFunctionHandler());
    }

    public static FunctionHandlerRegistry getInstance() {
        return INSTANCE;
    }

    public void registry(final Class<? extends Function.FunctionHandler> key, final Function.FunctionHandler handler) {
        this.handlers.put(key, handler);
    }

    public Function.FunctionHandler get(final Class<? extends Function.FunctionHandler> key) {

        return this.handlers.get(key);
    }

}


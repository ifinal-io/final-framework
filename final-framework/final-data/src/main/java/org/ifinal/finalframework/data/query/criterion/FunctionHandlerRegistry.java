package org.ifinal.finalframework.data.query.criterion;


import org.ifinal.finalframework.query.annotation.Function;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class FunctionHandlerRegistry {
    private static final FunctionHandlerRegistry INSTANCE = new FunctionHandlerRegistry();
    private final Map<Class<? extends Function.FunctionHandler>, Function.FunctionHandler> handlers = new ConcurrentHashMap<>(8);

    private FunctionHandlerRegistry() {
        registry(Function.FunctionHandler.class, new VelocityFunctionHandler());
    }

    public static FunctionHandlerRegistry getInstance() {
        return INSTANCE;
    }

    public void registry(Class<? extends Function.FunctionHandler> key, Function.FunctionHandler handler) {
        this.handlers.put(key, handler);
    }

    public Function.FunctionHandler get(Class<? extends Function.FunctionHandler> key) {
        return this.handlers.get(key);
    }


}


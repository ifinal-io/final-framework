

package org.finalframework.data.query.criterion;


import org.finalframework.annotation.query.Function;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-20 12:59:46
 * @since 1.0
 */
public class FunctionHandlerRegistry {
    private static final FunctionHandlerRegistry INSTANCE = new FunctionHandlerRegistry();
    private final Map<Class<? extends Function.FunctionHandler>, Function.FunctionHandler> handlers = new ConcurrentHashMap<>(8);

    {
        handlers.put(Function.FunctionHandler.class, new VelocityFunctionHandler());
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


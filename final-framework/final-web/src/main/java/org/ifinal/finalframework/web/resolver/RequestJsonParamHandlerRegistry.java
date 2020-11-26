package org.ifinal.finalframework.web.resolver;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class RequestJsonParamHandlerRegistry {
    private static final RequestJsonParamHandlerRegistry instance = new RequestJsonParamHandlerRegistry();
    private final Map<Class<?>, RequestJsonParamHandler<?>> requestJsonParamHandlers = new ConcurrentHashMap<>(64);

    private RequestJsonParamHandlerRegistry() {
    }

    public static RequestJsonParamHandlerRegistry getInstance() {
        return instance;
    }

    public <T> void register(Class<T> type, RequestJsonParamHandler<T> handler) {
        requestJsonParamHandlers.put(type, handler);
    }

    public <T> RequestJsonParamHandler<T> getRequestJsonParamHandler(Class<T> type) {
        return (RequestJsonParamHandler<T>) requestJsonParamHandlers.get(type);
    }

}
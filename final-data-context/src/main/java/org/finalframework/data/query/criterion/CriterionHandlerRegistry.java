

package org.finalframework.data.query.criterion;


import org.finalframework.annotation.query.CriterionHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-20 12:59:46
 * @since 1.0
 */
public class CriterionHandlerRegistry {
    private static final CriterionHandlerRegistry INSTANCE = new CriterionHandlerRegistry();
    private final Map<Class<? extends CriterionHandler>, CriterionHandler> handlers = new ConcurrentHashMap<>(8);

    {
        handlers.put(CriterionHandler.class, new VelocityCriterionHandler());
    }

    public static CriterionHandlerRegistry getInstance() {
        return INSTANCE;
    }

    public void registry(Class<? extends CriterionHandler> key, CriterionHandler handler) {
        this.handlers.put(key, handler);
    }

    public CriterionHandler get(Class<? extends CriterionHandler> key) {
        return this.handlers.get(key);
    }


}


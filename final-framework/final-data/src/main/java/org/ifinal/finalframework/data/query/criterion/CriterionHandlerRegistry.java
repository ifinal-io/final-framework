package org.ifinal.finalframework.data.query.criterion;


import org.ifinal.finalframework.query.annotation.CriterionSqlProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class CriterionHandlerRegistry {
    private static final CriterionHandlerRegistry INSTANCE = new CriterionHandlerRegistry();
    private final Map<Class<? extends CriterionSqlProvider>, CriterionSqlProvider> handlers = new ConcurrentHashMap<>(8);

    private CriterionHandlerRegistry() {
        registry(CriterionSqlProvider.class, new VelocityCriterionSqlProvider());
    }

    public static CriterionHandlerRegistry getInstance() {
        return INSTANCE;
    }


    public void registry(Class<? extends CriterionSqlProvider> key, CriterionSqlProvider handler) {
        this.handlers.put(key, handler);
    }

    public CriterionSqlProvider get(Class<? extends CriterionSqlProvider> key) {
        return this.handlers.get(key);
    }


}


package org.finalframework.data.query.criterion;


import org.finalframework.annotation.query.CriterionSqlProvider;

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
    private final Map<Class<? extends CriterionSqlProvider>, CriterionSqlProvider> handlers = new ConcurrentHashMap<>(8);

    {
        handlers.put(CriterionSqlProvider.class, new VelocityCriterionSqlProvider());
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


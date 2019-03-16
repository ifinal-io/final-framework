package org.finalframework.data.mapping;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-22 09:33:08
 * @since 1.0
 */
public final class DialectFactory {

    private static final String ROOT = "root";
    private static final Map<String, Dialect> cache = new ConcurrentHashMap<>(256);

    static {
        cache.put(ROOT, Dialect.DEFAULT);
    }

    public static void setDialect(String name, Dialect dialect) {
        cache.put(name, dialect);
    }

    public static Dialect getDialect(Class mapperClazz) {
        return getDialect(mapperClazz.getCanonicalName());
    }

    public static Dialect getDialect(String name) {
        String canonicalName = name;
        do {
            if (!cache.containsKey(canonicalName)) {
                canonicalName = canonicalName.substring(0, canonicalName.lastIndexOf("."));
                continue;
            }
            return cache.get(canonicalName);
        } while (canonicalName.contains("."));
        return cache.get(ROOT);
    }


}

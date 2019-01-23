package com.ilikly.finalframework.data.mapping;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-21 21:35:37
 * @since 1.0
 */
public class Dialect {
    @Getter
    private final String value;
    public static final Dialect DEFAULT = new Dialect("DEFAULT");
    public static final Dialect POST_GRE = new Dialect("POST_GRE");

    private static final Map<String, Dialect> cache = new HashMap<>(8);

    static {
        cache.put(POST_GRE.value, POST_GRE);
    }

    public static Dialect from(String dialect) {
        return cache.getOrDefault(dialect, DEFAULT);
    }

    public Dialect(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

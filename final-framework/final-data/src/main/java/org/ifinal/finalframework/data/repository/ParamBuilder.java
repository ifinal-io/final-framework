package org.ifinal.finalframework.data.repository;

import org.ifinal.finalframework.core.IQuery;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ParamBuilder.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ParamBuilder {

    public static final String PARAM_TABLE_NAME = "table";

    public static final String PARAM_VIEW_NAME = "view";

    public static final String PARAM_QUERY_NAME = "query";

    private final Map<String, Object> params = new LinkedHashMap<>();

    public ParamBuilder table(String table) {
        return put(PARAM_TABLE_NAME, table);
    }

    public ParamBuilder view(Class<?> view) {
        return put(PARAM_VIEW_NAME, view);
    }

    public ParamBuilder query(IQuery query) {
        return put(PARAM_QUERY_NAME, query);
    }

    public ParamBuilder put(String name, Object value) {
        this.params.put(name, value);
        return this;
    }

    public Map<String, Object> build() {
        return params;
    }

}

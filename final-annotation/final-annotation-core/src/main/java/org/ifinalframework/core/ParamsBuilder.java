/*
 * Copyright 2020-2021 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.core;


import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ParamsBuilder.
 *
 * @author iimik
 * @version 1.2.2
 * @see IRepository
 * @since 1.2.2
 */
public class ParamsBuilder<I, T> {

    public static final String TABLE_PARAM_NAME = "table";

    public static final String TENANT_PARAM_NAME = "tenant";

    public static final String VIEW_PARAM_NAME = "view";
    public static final String IGNORE_PARAM_NAME = "ignore";
    public static final String SELECTIVE_PARAM_NAME = "selective";
    public static final String LIST_PARAM_NAME = "list";
    public static final String UPDATE_PARAM_NAME = "update";
    public static final String ENTITY_PARAM_NAME = "entity";
    public static final String ID_PARAM_NAME = "id";
    public static final String IDS_PARAM_NAME = "ids";
    public static final String QUERY_PARAM_NAME = "query";

    private final Map<String, Object> params = new LinkedHashMap<>(8);

    private ParamsBuilder() {
    }

    public static <I, T> ParamsBuilder<I, T> builder() {
        return new ParamsBuilder<>();
    }

    public ParamsBuilder<I, T> table(String table) {
        params.put(TABLE_PARAM_NAME, table);
        return this;
    }

    public ParamsBuilder<I, T> view(Class<?> view) {
        params.put(VIEW_PARAM_NAME, view);
        return this;
    }

    public ParamsBuilder<I, T> ignore(boolean ignore) {
        params.put(IGNORE_PARAM_NAME, ignore);
        return this;
    }

    public ParamsBuilder<I, T> selective(boolean selective) {
        params.put(SELECTIVE_PARAM_NAME, selective);
        return this;
    }

    public ParamsBuilder<I, T> update(T entity) {
        params.put(ENTITY_PARAM_NAME, entity);
        return this;
    }

    public ParamsBuilder<I, T> update(IUpdate update) {
        params.put(UPDATE_PARAM_NAME, update);
        return this;
    }

    public ParamsBuilder<I, T> list(Collection<T> list) {
        params.put(LIST_PARAM_NAME, list);
        return this;
    }


    public ParamsBuilder<I, T> id(I id) {
        params.put(ID_PARAM_NAME, id);
        return this;
    }

    public ParamsBuilder<I, T> ids(Collection<I> list) {
        params.put(IDS_PARAM_NAME, list);
        return this;
    }

    public ParamsBuilder<I, T> query(IQuery query) {
        params.put(QUERY_PARAM_NAME, query);
        return this;
    }


    public Map<String, Object> build() {
        return params;
    }
}

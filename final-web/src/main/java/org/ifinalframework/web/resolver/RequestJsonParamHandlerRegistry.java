/*
 * Copyright 2020-2021 the original author or authors.
 *
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

package org.ifinalframework.web.resolver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class RequestJsonParamHandlerRegistry {

    private static final RequestJsonParamHandlerRegistry instance = new RequestJsonParamHandlerRegistry();

    private final Map<Class<?>, RequestJsonParamHandler<?>> requestJsonParamHandlers = new ConcurrentHashMap<>(64);

    private RequestJsonParamHandlerRegistry() {
    }

    public static RequestJsonParamHandlerRegistry getInstance() {
        return instance;
    }

    public <T> void register(final Class<T> type, final RequestJsonParamHandler<T> handler) {

        requestJsonParamHandlers.put(type, handler);
    }

    public <T> RequestJsonParamHandler<T> getRequestJsonParamHandler(final Class<T> type) {

        return (RequestJsonParamHandler<T>) requestJsonParamHandlers.get(type);
    }

}

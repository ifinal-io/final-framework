/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.spring.web.resolver;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2019-04-12 16:45:59
 * @since 1.0
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

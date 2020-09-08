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

package org.finalframework.data.query.criterion;


import org.finalframework.annotation.query.Function;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2020-07-20 12:59:46
 * @since 1.0
 */
public class FunctionHandlerRegistry {
    private static final FunctionHandlerRegistry INSTANCE = new FunctionHandlerRegistry();
    private final Map<Class<? extends Function.FunctionHandler>, Function.FunctionHandler> handlers = new ConcurrentHashMap<>(8);

    {
        handlers.put(Function.FunctionHandler.class, new VelocityFunctionHandler());
    }

    public static FunctionHandlerRegistry getInstance() {
        return INSTANCE;
    }

    public void registry(Class<? extends Function.FunctionHandler> key, Function.FunctionHandler handler) {
        this.handlers.put(key, handler);
    }

    public Function.FunctionHandler get(Class<? extends Function.FunctionHandler> key) {
        return this.handlers.get(key);
    }


}


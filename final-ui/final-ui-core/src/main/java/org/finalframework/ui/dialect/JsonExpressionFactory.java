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

package org.finalframework.ui.dialect;


import org.finalframework.json.JsonRegistry;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-09 16:15:42
 * @since 1.0
 */
public class JsonExpressionFactory implements IExpressionObjectFactory {

    private final String name;

    public JsonExpressionFactory(String name) {
        this.name = name;
    }

    @Override
    public Set<String> getAllExpressionObjectNames() {
        return new HashSet<>(Collections.singletonList("json"));
    }

    @Override
    public Object buildObject(IExpressionContext iExpressionContext, String name) {
        return this.name.equals(name) ? JsonRegistry.getInstance().getJsonService() : null;
    }

    @Override
    public boolean isCacheable(String name) {
        return this.name.equals(name);
    }
}


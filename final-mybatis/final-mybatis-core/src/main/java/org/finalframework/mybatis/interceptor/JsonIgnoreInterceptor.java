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

package org.finalframework.mybatis.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.finalframework.json.context.JsonContextHolder;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-15 21:58:13
 * @since 1.0
 */
@SuppressWarnings({"rawtypes"})
@Intercepts(
        {
                @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
        }
)
@SpringComponent
public class JsonIgnoreInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(JsonIgnoreInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        boolean ignoreChanged = false;
        try {
            if (!JsonContextHolder.isIgnore()) {
                JsonContextHolder.setIgnore(true, true);
                ignoreChanged = true;
            }
            return invocation.proceed();
        } finally {
            if (ignoreChanged) {
                JsonContextHolder.setIgnore(false, false);
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

}

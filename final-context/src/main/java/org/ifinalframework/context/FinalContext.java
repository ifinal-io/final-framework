/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.context;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;

/**
 * FinalContext.
 *
 * @author ilikly
 * @version 1.4.3
 * @since 1.4.3
 */
@RequiredArgsConstructor
public enum FinalContext {
    TENANT("tenant", "租户"),
    SCOPE("scope", "作用域"),
    ;
    private static final ThreadLocal<Map<String, String>> CONTEXT = ThreadLocal.withInitial(LinkedHashMap::new);

    private final String code;
    private final String desc;


    public static void reset() {
        CONTEXT.get().clear();
    }

    public String get() {
        return CONTEXT.get().get(code);
    }

    public void set(String value) {
        CONTEXT.get().put(code, value);
    }


}

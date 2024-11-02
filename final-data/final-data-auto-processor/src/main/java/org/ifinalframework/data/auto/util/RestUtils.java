/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.data.auto.util;

import lombok.experimental.UtilityClass;

/**
 * RestUtils.
 *
 * @author iimik
 * @version 1.4.0
 * @since 1.4.0
 */
@UtilityClass
public class RestUtils {
    public static String packageName(Class<?> clazz) {
        return clazz.getPackage().getName().replace(".entity", ".web.controller");
    }

    public static String controllerName(Class<?> clazz) {
        return clazz.getSimpleName() + "RestController";
    }

    public static String controllerClassName(Class<?> clazz) {
        return String.join(".", packageName(clazz), controllerName(clazz));
    }
}



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

package org.finalframework.coding.tool;


import org.apache.velocity.tools.config.DefaultKey;
import org.springframework.lang.NonNull;

import javax.lang.model.element.TypeElement;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-21 16:12:25
 * @since 1.0
 */
@DefaultKey("java")
public class JavaTool {

    public static String name(@NonNull TypeElement element) {
        return element.getQualifiedName().toString().replaceAll("java.lang", "");
    }

    public static String simpleName(@NonNull TypeElement element) {
        return element.getSimpleName().toString();
    }

}


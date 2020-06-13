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

package org.finalframework.core.generator;

import org.springframework.lang.NonNull;

import static java.util.Locale.ENGLISH;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-15 15:26:58
 * @since 1.0
 */
public interface NameGenerator {
    /**
     * Returns a String which capitalizes the first letter of the string.
     */
    static String capitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        return name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
    }

    static String capitalize(@NonNull String prefix, String name) {
        return prefix + capitalize(name);
    }

    static String decapitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }

        int pos = 0;
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) >= 'A' && name.charAt(i) <= 'Z') {
                pos++;
            } else {
                break;
            }
        }

        return name.substring(0, pos).toLowerCase(ENGLISH) + name.substring(pos);
    }

    static String decapitalize(String name, String prefix) {
        return decapitalize(name.substring(prefix.length()));
    }
}

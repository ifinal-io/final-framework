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

package org.ifinalframework.core.generator;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.Locale;

/**
 * NameGenerator.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface NameGenerator {

    /**
     * Returns a String which capitalizes the first letter of the string.
     */
    static String capitalize(final String name) {
        Assert.hasText(name, "the name must be not empty!");
        return name.substring(0, 1).toUpperCase(Locale.ENGLISH) + name.substring(1);
    }

    /**
     * Returns a String which capitalizes the first letter of the string with {@code prefix}.
     */
    static String capitalize(final @NonNull String prefix, final String name) {
        return prefix + capitalize(name);
    }

    static String decapitalize(final String name) {

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

        return name.substring(0, pos).toLowerCase(Locale.ENGLISH) + name.substring(pos);
    }

    static String decapitalize(final String name, final String prefix) {

        return decapitalize(name.substring(prefix.length()));
    }

}

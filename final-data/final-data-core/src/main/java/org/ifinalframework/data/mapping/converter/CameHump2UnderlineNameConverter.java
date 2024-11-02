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

package org.ifinalframework.data.mapping.converter;

import org.ifinalframework.data.annotation.NameConverter;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class CameHump2UnderlineNameConverter implements NameConverter {

    @Override
    public String convert(final String name) {

        StringBuilder sb = new StringBuilder(name);
        int temp = 0;
        for (int i = 1; i < name.length(); i++) {
            if (Character.isUpperCase(name.charAt(i)) && !Character.isUpperCase(name.charAt(i - 1))
                    && '_' != name.charAt(i - 1) && ' ' != name.charAt(i - 1)) {
                sb.insert(i + temp, "_");
                temp += 1;
            }
        }
        return sb.toString().toLowerCase();
    }

}

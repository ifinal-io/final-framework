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
 *
 */

package org.ifinal.finalframework.util;

import java.awt.Color;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Colors.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Colors {

    private static final Map<String, Color> CACHE = new HashMap<>(64);

    static {
        CACHE.put("RED", Color.RED);
    }

    public Colors() {
    }

    public static Color decode(String color){
        if (color.startsWith("#")) {
            return Color.decode(color.replace("#", "0x"));
        }

        if (color.startsWith("0x") || color.startsWith("0X")) {
            return Color.decode(color);
        }

        return CACHE.get(color.toUpperCase(Locale.ROOT));
    }

}

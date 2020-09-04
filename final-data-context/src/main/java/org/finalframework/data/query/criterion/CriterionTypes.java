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

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Properties;

import org.finalframework.core.parser.xml.PropertyParser;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.function.CriterionFunction;
import org.springframework.lang.NonNull;

/**
 * @author sli
 * @version 1.0
 * @date 2020-06-01 21:00:52
 * @since 1.0
 */
public abstract class CriterionTypes {

    public static boolean isProperty(@NonNull Object value) {
        return value instanceof QProperty;
    }

    public static boolean isFunction(@NonNull Object value) {
        return value instanceof CriterionFunction;
    }

    public static boolean isCollection(@NonNull Object value) {
        return value instanceof Collection;
    }

    public static boolean isArray(@NonNull Object value) {
        return value instanceof Array;
    }

    public static boolean isValue(@NonNull Object value) {
        return value instanceof CriterionValue;
    }

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("value", "criterion.value");
        System.out.println(PropertyParser.parse("\\${${value}.column}", properties));
    }


}

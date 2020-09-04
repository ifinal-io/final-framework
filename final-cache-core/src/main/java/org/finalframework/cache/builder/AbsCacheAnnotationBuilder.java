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

package org.finalframework.cache.builder;


import org.finalframework.core.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 09:44:18
 * @since 1.0
 */
public class AbsCacheAnnotationBuilder {
    private static final String DELIMITER = ":";

    protected static Collection<String> parse(String[] keyOrField, String delimiter) {
        if (Assert.isEmpty(keyOrField)) {
            return null;
        }
        final String split = getDelimiter(delimiter);
        List<String> list = new ArrayList<>();
        Arrays.stream(keyOrField)
                .map(item -> item.split(split))
                .forEach(items -> list.addAll(Arrays.asList(items)));
        return list;
    }

    protected static String getDelimiter(String delimiter) {
        return Assert.isBlank(delimiter) ? DELIMITER : delimiter.trim();
    }


}

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

package org.finalframework.data.query.builder;


import org.finalframework.data.query.Sort;

import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-06 14:27:34
 * @since 1.0
 */
public class SortSqlBuilder implements SqlBuilder<Sort> {

    private final Sort sort;

    public SortSqlBuilder(Sort sort) {
        this.sort = sort;
    }

    @Override
    public String build() {
        if (sort == null) return "";
        return sort.stream()
                .map(it -> String.format("%s %s", formatProperty(it.getProperty()), it.getDirection().name()))
                .collect(Collectors.joining(","));
    }
}

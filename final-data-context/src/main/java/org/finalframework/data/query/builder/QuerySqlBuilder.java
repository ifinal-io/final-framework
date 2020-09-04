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

import org.finalframework.data.query.Query;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-22 19:58:40
 * @since 1.0
 */
public class QuerySqlBuilder implements SqlBuilder<Query> {

    private final Query query;

    public QuerySqlBuilder(Query query) {
        this.query = query;
    }

    @Override
    public String build() {
        if (query == null) return "";

        final StringBuilder sb = new StringBuilder();

//        String criteria = query.getCriteria().stream().map(it -> new CriteriaSqlBuilder(it).build()).collect(Collectors.joining(" AND "));

//        sb.append(criteria);

        if (query.getSort() != null) {
            sb.append(" ORDER BY ").append(new SortSqlBuilder(query.getSort()).build());
        }

        if (query.getLimit() != null) {
            sb.append(" LIMIT ").append(query.getLimit());
        }

        return sb.toString();
    }


}

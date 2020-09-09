

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

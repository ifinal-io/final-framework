package org.finalframework.data.query.builder;

import org.finalframework.data.query.QueryImpl;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-22 19:58:40
 * @since 1.0
 */
public class QuerySqlBuilder implements SqlBuilder<QueryImpl> {

    private final QueryImpl query;

    public QuerySqlBuilder(QueryImpl query) {
        this.query = query;
    }

    @Override
    public String build() {
        if (query == null) return "";

        final StringBuilder sb = new StringBuilder();
//        final Criteria criteria = query.getCriteria();
//        if (criteria != null) {
//            sb.append(new CriteriaSqlBuilder(criteria).build());
//        }
//        if (query.getSort() != null) {
//            sb.append(" ORDER BY ").append(new SortSqlBuilder(query.getSort()).build());
//        }
//
//        if (query.getLimit() != null) {
//            sb.append(" LIMIT ").append(query.getLimit());
//        }

        return sb.toString();
    }


}

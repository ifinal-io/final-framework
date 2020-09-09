

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

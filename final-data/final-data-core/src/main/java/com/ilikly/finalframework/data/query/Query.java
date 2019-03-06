package com.ilikly.finalframework.data.query;

import com.ilikly.finalframework.core.Streamable;
import com.ilikly.finalframework.data.provider.QuerySqlBuilder;
import com.ilikly.finalframework.data.query.enums.Direction;
import lombok.Getter;
import lombok.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 21:15
 * @since 1.0
 */
public class Query implements Streamable<Criteria>, Serializable, Pageable {

    @Getter
    private Integer page;
    @Getter
    private Integer size;
    @Getter
    private List<Criteria> criteria = new ArrayList<>();
    @Getter
    private Sort sort;
    @Getter
    private Integer limit;

    public Query page(int page) {
        this.page = page;
        return this;
    }

    public Query size(int size) {
        this.size = size;
        return this;
    }

    public Query where(@NonNull Criteria... criteria) {
        return where(Arrays.asList(criteria));
    }

    public Query where(@NonNull Collection<Criteria> criteria) {
        this.criteria.addAll(criteria);
        return this;
    }

    public Query sort(@NonNull Order... orders) {
        return sort(Arrays.asList(orders));
    }

    public Query sort(Sort sort) {
        if (this.sort == null) {
            this.sort = sort;
        } else {
            this.sort.and(sort);
        }
        return this;
    }

    public Query sort(@NonNull Collection<Order> orders) {
        sort = sort == null ? Sort.by(new ArrayList<>(orders)) : sort.and(Sort.by(new ArrayList<>()));
        return this;
    }

    public Query sort(@NonNull Direction direction, @NonNull QProperty... properties) {
        return sort(Sort.sort(direction, properties));
    }

    public Query asc(@NonNull QProperty... properties) {
        return sort(Direction.ASC, properties);
    }

    public Query desc(@NonNull QProperty... properties) {
        return sort(Direction.DESC, properties);
    }

    public Query limit(int limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public Stream<Criteria> stream() {
        return criteria.stream();
    }

    public String getSql() {
        return new QuerySqlBuilder(this).provide();
    }


}

package org.finalframework.data.query;

import lombok.Getter;
import lombok.NonNull;
import org.finalframework.core.Streamable;
import org.finalframework.data.query.builder.QuerySqlBuilder;
import org.finalframework.data.query.enums.Direction;

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
public class Query implements Streamable<Criteria>, Serializable, Pageable, Sql<Query> {

    /**
     * 页码，第一页从1开始
     */
    @Getter
    private Integer page;
    /**
     * 页面容量
     */
    @Getter
    private Integer size;
    /**
     * 是否进行Count查询
     */
    @Getter
    private Boolean count = Boolean.TRUE;
    @Getter
    private Boolean reasonable = Boolean.FALSE;
    @Getter
    private Boolean pageSizeZero = Boolean.FALSE;
    @Getter
    private List<Criteria> criteria = new ArrayList<>();
    @Getter
    private Sort sort;
    @Getter
    private Limit limit;

    public Query page(Integer page, Integer size) {
        this.page = page;
        this.size = size;
        return this;
    }

    public Query page(Integer page) {
        this.page = page;
        return this;
    }

    public Query size(Integer size) {
        this.size = size;
        return this;
    }

    public Query count(Boolean count) {
        this.count = count;
        return this;
    }

    public Query reasonable(Boolean reasonable) {
        this.reasonable = reasonable;
        return this;
    }

    public Query pageSizeZero(Boolean pageSizeZero) {
        this.pageSizeZero = pageSizeZero;
        return this;
    }

    public Query where(@NonNull Criteria... criteria) {
        return where(Arrays.asList(criteria));
    }

    public Query where(@NonNull Collection<Criteria> criteria) {
        this.criteria.addAll(criteria);
        return this;
    }

    public Query where(Criterion... criterion) {
        return where(Arrays.asList(criterion));
    }

    public Query where(List<Criterion> criterion) {
        this.where(Criteria.where(criterion));
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

    public Query limit(long offset, long limit) {
        this.limit = new LimitImpl(offset, limit);
        return this;
    }

    public Query limit(long limit) {
        this.limit = new LimitImpl(null, limit);
        return this;
    }

    @Override
    public Stream<Criteria> stream() {
        return criteria.stream();
    }

    @Override
    public String getSql() {
        return new QuerySqlBuilder(this).build();
    }


}

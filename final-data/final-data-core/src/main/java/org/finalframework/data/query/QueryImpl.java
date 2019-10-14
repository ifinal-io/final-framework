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
public class QueryImpl implements Query, Streamable<Criteria>, Serializable, Pageable, Sql<QueryImpl> {

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
    private Boolean count;
    @Getter
    private Boolean reasonable;
    @Getter
    private Boolean pageSizeZero;
    @Getter
    private List<Criteria> criteria = new ArrayList<>();
    @Getter
    private Sort sort;
    @Getter
    private Limit limit;

    public QueryImpl page(Integer page, Integer size) {
        this.page = page;
        this.size = size;
        return this;
    }

    @Deprecated
    public QueryImpl page(Integer page) {
        this.page = page;
        return this;
    }

    @Deprecated
    public QueryImpl size(Integer size) {
        this.size = size;
        return this;
    }

    public QueryImpl count(Boolean count) {
        this.count = count;
        return this;
    }

    public QueryImpl reasonable(Boolean reasonable) {
        this.reasonable = reasonable;
        return this;
    }

    public QueryImpl pageSizeZero(Boolean pageSizeZero) {
        this.pageSizeZero = pageSizeZero;
        return this;
    }

    public QueryImpl where(@NonNull Criteria... criteria) {
        return where(Arrays.asList(criteria));
    }

    public QueryImpl where(@NonNull Collection<Criteria> criteria) {
        this.criteria.addAll(criteria);
        return this;
    }

    public QueryImpl where(Criterion... criterion) {
        return where(Arrays.asList(criterion));
    }

    public QueryImpl where(List<Criterion> criterion) {
        this.where(Criteria.where(criterion));
        return this;
    }

    public QueryImpl sort(@NonNull Order... orders) {
        return sort(Arrays.asList(orders));
    }

    public QueryImpl sort(Sort sort) {
        if (this.sort == null) {
            this.sort = sort;
        } else {
            this.sort.and(sort);
        }
        return this;
    }

//    public Criteria getCriteria() {
//        return criteria.isEmpty() ? null : CriteriaImpl.from(criteria);
//    }

    public QueryImpl sort(@NonNull Collection<Order> orders) {
        sort = sort == null ? Sort.by(new ArrayList<>(orders)) : sort.and(Sort.by(new ArrayList<>()));
        return this;
    }

    public QueryImpl sort(@NonNull Direction direction, @NonNull QProperty... properties) {
        return sort(Sort.sort(direction, properties));
    }

    public QueryImpl asc(@NonNull QProperty... properties) {
        return sort(Direction.ASC, properties);
    }

    public QueryImpl desc(@NonNull QProperty... properties) {
        return sort(Direction.DESC, properties);
    }

    public QueryImpl limit(long offset, long limit) {
        this.limit = new LimitImpl(offset, limit);
        return this;
    }

    public QueryImpl limit(long limit) {
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

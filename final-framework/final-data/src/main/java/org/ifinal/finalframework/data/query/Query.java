package org.ifinal.finalframework.data.query;

import org.springframework.lang.NonNull;

import org.ifinal.finalframework.annotation.core.Pageable;
import org.ifinal.finalframework.query.Criteria;
import org.ifinal.finalframework.query.Criterion;
import org.ifinal.finalframework.query.Direction;
import org.ifinal.finalframework.query.Groupable;
import org.ifinal.finalframework.query.Limitable;
import org.ifinal.finalframework.query.Orderable;
import org.ifinal.finalframework.util.stream.Streamable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.Setter;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class Query implements Streamable<Criterion>, Groupable, Orderable, Limitable, Pageable {

    @Getter
    private final Criteria criteria = new Criteria();

    /**
     * 页码，第一页从1开始
     */
    @Getter
    @Setter
    private Integer page;

    /**
     * 页面容量
     */
    @Setter
    @Getter
    private Integer size;

    /**
     * 是否进行Count查询
     */
    @Setter
    @Getter
    private Boolean count = Boolean.TRUE;

    @Getter
    private Long offset;

    @Getter
    private Long limit;

    @Getter
    private final List<String> orders = new LinkedList<>();

    @Getter
    private final List<String> groups = new LinkedList<>();

    public Query page(final Integer page, final Integer size) {

        this.page = page;
        this.size = size;
        return this;
    }

    public Query page(final Integer page) {

        this.page = page;
        return this;
    }

    public Query size(final Integer size) {

        this.size = size;
        return this;
    }

    public Query count(final Boolean count) {

        this.count = count;
        return this;
    }

    public Query where(final @NonNull Criterion... criteria) {

        return where(Arrays.asList(criteria));
    }

    public Query where(final @NonNull Collection<Criterion> criteria) {
        this.criteria.addAll(criteria);
        return this;
    }

    public Query group(final QProperty<?>... properties) {

        return group(Arrays.asList(properties));
    }

    public Query group(final Collection<QProperty<?>> properties) {
        properties.forEach(it -> this.groups.add(it.getColumn()));
        return this;
    }

    public Query sort(final @NonNull Order... orders) {
        return sort(Arrays.asList(orders));
    }

    public Query sort(final Sort sort) {
        sort.forEach(it -> this.orders.add(it.getProperty().getColumn() + " " + it.getDirection().name()));
        return this;
    }

    public Query sort(final @NonNull Collection<Order> orders) {
        return this.sort(Sort.by(new ArrayList<>(orders)));
    }

    public Query sort(final @NonNull Direction direction, final @NonNull QProperty... properties) {
        return sort(Sort.sort(direction, properties));
    }

    public Query asc(final @NonNull QProperty... properties) {
        return sort(Direction.ASC, properties);
    }

    public Query desc(final @NonNull QProperty... properties) {

        return sort(Direction.DESC, properties);
    }

    public Query limit(final long offset, final long limit) {
        this.offset = offset;
        this.limit = limit;
        return this;
    }

    public Query limit(final long limit) {
        this.offset = null;
        this.limit = limit;
        return this;
    }

    @Override
    public Stream<Criterion> stream() {
        return criteria.stream();
    }

}

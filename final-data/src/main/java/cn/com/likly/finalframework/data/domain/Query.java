package cn.com.likly.finalframework.data.domain;

import cn.com.likly.finalframework.data.domain.enums.Direction;
import lombok.Getter;
import lombok.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 21:15
 * @since 1.0
 */
@Getter
public class Query<T> implements Serializable {
    private Integer page;
    private Integer size;
    private List<Criteria<T>> criteria;
    private Sort sort;
    private Integer limit;

    public Query<T> page(int page) {
        this.page = page;
        return this;
    }

    public Query<T> size(int size) {
        this.size = size;
        return this;
    }

    public Query<T> where(@NonNull Criteria<T>... criteria) {
        return where(Arrays.asList(criteria));
    }

    public Query<T> where(@NonNull Collection<Criteria<T>> criteria) {
        if (this.criteria == null) {
            this.criteria = new ArrayList<>();
        }
        this.criteria.addAll(criteria);
        return this;
    }

    public Query<T> sort(@NonNull Order<T>... orders) {
        return sort(Arrays.asList(orders));
    }

    public Query<T> sort(@NonNull Collection<Order<T>> orders) {
        sort = sort == null ? Sort.by(new ArrayList<>(orders)) : sort.and(Sort.by(new ArrayList<>()));
        return this;
    }

    public Query<T> sort(@NonNull Direction direction, @NonNull T... properties) {
        return sort(Arrays.stream(properties).map(it -> new Order<>(it, direction)).collect(Collectors.toList()));
    }

    public Query<T> asc(@NonNull T... properties) {
        return sort(Direction.ASC, properties);
    }

    public Query<T> desc(@NonNull T... properties) {
        return sort(Direction.DESC, properties);
    }

    public Query<T> limit(int limit) {
        this.limit = limit;
        return this;
    }

    public Stream<Criteria<T>> stream() {
        return criteria.stream();
    }

}

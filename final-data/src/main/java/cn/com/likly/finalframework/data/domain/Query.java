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

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 21:15
 * @since 1.0
 */
@Getter
public class Query implements Serializable {
    private Integer page;
    private Integer size;
    private List<Criteria> criteria;
    private Sort sort;
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
        if (this.criteria == null) {
            this.criteria = new ArrayList<>();
        }
        this.criteria.addAll(criteria);
        return this;
    }

    public Query sort(@NonNull Order... orders) {
        return sort(Arrays.asList(orders));
    }

    public Query sort(@NonNull Collection<Order> orders) {
        sort = sort == null ? Sort.by(new ArrayList<>(orders)) : sort.and(Sort.by(new ArrayList<>()));
        return this;
    }

    public Query sort(@NonNull Direction direction, @NonNull String... properties) {
        return sort(Arrays.stream(properties).map(it -> new Order(it, direction)).collect(Collectors.toList()));
    }

    public Query asc(@NonNull String... properties) {
        return sort(Direction.ASC, properties);
    }

    public Query desc(@NonNull String... properties) {
        return sort(Direction.DESC, properties);
    }

    public Query limit(int limit) {
        this.limit = limit;
        return this;
    }

}

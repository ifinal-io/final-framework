package cn.com.likly.finalframework.mybatis.criteria;

import cn.com.likly.finalframework.mybatis.criteria.enums.AndOr;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 14:29
 * @since 1.0
 */
@Data
public final class Criteria {
    private final Integer page;
    private final Integer size;
    private final List<SetCriteria> sets;
    private final List<WhereCriteria> where;
    private final List<OrderCriteria> order;
    private final Integer limit;

    private Criteria(Builder builder) {
        this.page = builder.page;
        this.size = builder.size;
        this.sets = builder.sets.isEmpty() ? null : Collections.unmodifiableList(builder.sets);
        this.where = builder.where.isEmpty() ? null : Collections.unmodifiableList(builder.where);
        this.order = builder.order.isEmpty() ? null : Collections.unmodifiableList(builder.order);
        this.limit = builder.limit;
    }

    public static Builder builder() {
        return new Builder();
    }


    public final static class Builder {
        private final List<SetCriteria> sets = new ArrayList<>();
        private final List<WhereCriteria> where = new ArrayList<>();
        private final List<OrderCriteria> order = new ArrayList<>();
        private Integer page;
        private Integer size;
        private Integer limit;

        private Builder() {
        }

        public Builder page(@NonNull Integer page) {
            this.page = page;
            return this;
        }

        public Builder size(@NonNull Integer size) {
            this.size = size;
            return this;
        }

        public ColumnCriteria.Builder<Criteria.Builder> and(String column) {
            return new ColumnCriteria.Builder<>(this, AndOr.AND, column);
        }

        public ColumnCriteria.Builder<Criteria.Builder> or(String column) {
            return new ColumnCriteria.Builder<>(this, AndOr.OR, column);
        }

        public WhereCriteria.Builder<Criteria.Builder> and() {
            return this.where(AndOr.AND);
        }

        public WhereCriteria.Builder<Criteria.Builder> or() {
            return this.where(AndOr.OR);
        }


        private WhereCriteria.Builder<Criteria.Builder> where(AndOr andOr) {
            return new WhereCriteria.Builder<>(this, andOr);
        }

        public Builder and(ColumnCriteria... columnCriteria) {
            return this.where(AndOr.AND, columnCriteria);
        }

        public Builder or(ColumnCriteria... columnCriteria) {
            return this.where(AndOr.OR, columnCriteria);
        }

        Builder where(AndOr andOr, ColumnCriteria... columnCriteria) {
            this.where.add(new WhereCriteria(andOr, Arrays.asList(columnCriteria)));
            return this;
        }


        Builder where(WhereCriteria... whereCriteria) {
            return this.where(Arrays.asList(whereCriteria));
        }

        Builder where(List<WhereCriteria> whereCriteria) {
            this.where.addAll(whereCriteria);
            return this;
        }


        public Builder asc(String... columns) {
            this.order.addAll(OrderCriteria.builder().asc(columns).build());
            return this;
        }

        public Builder desc(String... columns) {
            this.order.addAll(OrderCriteria.builder().desc(columns).build());
            return this;
        }

        public Builder limit(@NonNull Integer limit) {
            this.limit = limit;
            return this;
        }

        public Criteria build() {
            return new Criteria(this);
        }
    }


}

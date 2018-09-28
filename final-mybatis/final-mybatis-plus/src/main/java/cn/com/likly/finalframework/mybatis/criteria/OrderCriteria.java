package cn.com.likly.finalframework.mybatis.criteria;

import cn.com.likly.finalframework.mybatis.criteria.enums.Order;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 14:28
 * @since 1.0
 */
@Data
public class OrderCriteria {
    private final String column;
    private final Order order;

    private OrderCriteria(String column, Order order) {
        this.column = column;
        this.order = order;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final List<OrderCriteria> order = new ArrayList<>();

        private Builder() {
        }

        public Builder asc(@NonNull String... columns) {
            this.order(Order.ASC, columns);
            return this;
        }

        public Builder desc(@NonNull String... columns) {
            this.order(Order.DESC, columns);
            return this;
        }


        private void order(Order order, String... columns) {
            for (String column : columns) {
                this.order.add(new OrderCriteria(column, order));
            }
        }


        public List<OrderCriteria> build() {
            return order.isEmpty() ? null : Collections.unmodifiableList(order);
        }

    }

}

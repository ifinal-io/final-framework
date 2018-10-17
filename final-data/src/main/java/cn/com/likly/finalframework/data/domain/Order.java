package cn.com.likly.finalframework.data.domain;

import cn.com.likly.finalframework.data.domain.enums.Direction;
import cn.com.likly.finalframework.util.Assert;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 21:17
 * @since 1.0
 */
@Slf4j
@Data
public class Order implements Serializable {

    private static final long serialVersionUID = 7926595035488476307L;
    private final String property;
    private final Direction direction;

    public Order(String property, Direction direction) {
        this.property = property;
        this.direction = direction;
    }

    public static List<Order> asc(String... property) {
        return of(Direction.ASC, property);
    }

    public static List<Order> desc(String... property) {
        return of(Direction.DESC, property);
    }

    private static List<Order> of(Direction direction, String... property) {
        if (Assert.isEmpty(property)) {
            throw new IllegalArgumentException("property is null or empty.");
        }

        return Arrays.stream(property).map(it -> new Order(it, direction)).collect(Collectors.toList());
    }


}

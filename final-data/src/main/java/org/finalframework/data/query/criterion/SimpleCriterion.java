package org.finalframework.data.query.criterion;

import org.finalframework.data.query.operation.Operation;
import org.springframework.lang.NonNull;

/**
 * [标准，准则，规范，准据] 条件
 * Criterion是最基本,最底层的Where条件，用于字段级的筛选，例如：字段 in | not in | like | > | >= | < | <= | is not null | is null 等
 *
 * <ul>
 *     <li>{@literal IS NULL | IS NOT NULL }{@link org.finalframework.data.query.condition.NullCondition}</li>
 *     <li>{@literal = | != | > | >= | < | <= }{@link org.finalframework.data.query.condition.CompareCondition}</li>
 *     <li>{@literal IN | NOT IN }{@link org.finalframework.data.query.condition.InCondition}</li>
 *     <li>{@literal BETWEEN AND | NOT BETWEEN AND }{@link org.finalframework.data.query.condition.BetweenCondition}</li>
 *     <li>{@literal LIKE | NOT LIKE }{@link org.finalframework.data.query.condition.LikeCondition}</li>
 * </ul>
 *
 * @author likly
 * @version 1.0
 * @date 2019-01-18 12:19:55
 * @see SingleCriterion
 * @since 1.0
 */
public interface SimpleCriterion<T> extends Criterion {

    @NonNull
    Object getTarget();

    @NonNull
    Operation getOperation();

    interface Builder<T, R extends Builder> extends org.finalframework.util.Builder<T> {

        @NonNull
        R target(Object target);

        @NonNull
        R operation(@NonNull Operation operation);

    }

}

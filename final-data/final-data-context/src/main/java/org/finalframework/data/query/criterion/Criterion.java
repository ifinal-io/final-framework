package org.finalframework.data.query.criterion;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.criterion.operator.CriterionOperator;
import org.finalframework.data.query.function.Executable;
import org.finalframework.data.query.function.operation.FunctionOperation;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collection;

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
 * @see BetweenCriterion
 * @since 1.0
 */
public interface Criterion extends Executable<Object, Criterion> {

    @NonNull
    CriterionProperty<?> getProperty();

    @Nullable
    Collection<FunctionOperation> getFunctions();

    @NonNull
    CriterionOperator getOperator();

    Criterion setTypeHandler(Class<? extends TypeHandler<?>> typeHandler);

    @Nullable
    Class<? extends TypeHandler<?>> getTypeHandler();

    interface Builder<T, R extends Builder> extends org.finalframework.core.Builder<T> {
        @NonNull
        default R property(@NonNull QProperty<?> property) {
            return property(property, null);
        }

        @NonNull
        R property(@NonNull QProperty<?> property, @Nullable Collection<FunctionOperation> functions);

        @NonNull
        R operator(@NonNull CriterionOperator operator);

        @NonNull
        R typeHandler(@NonNull Class<? extends TypeHandler<?>> typeHandler);

    }

}

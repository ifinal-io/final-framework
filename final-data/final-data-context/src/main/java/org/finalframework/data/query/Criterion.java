package org.finalframework.data.query;

import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.query.criterion.BetweenCriterion;
import org.finalframework.data.query.criterion.LikeCriterion;
import org.finalframework.data.query.criterion.SingleCriterion;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 12:19:55
 * @see SingleCriterion
 * @see BetweenCriterion
 * @see LikeCriterion
 * @since 1.0
 */
public interface Criterion<T> {

    @NonNull
    QProperty getProperty();

    @Nullable
    Collection<FunctionCriterion> getFunctions();

    @NonNull
    CriterionOperator getOperator();

    @Nullable
    Class<? extends TypeHandler> getTypeHandler();

    String getColumn();


    interface Builder<T, R extends Builder> extends org.finalframework.core.Builder<T> {
        @NonNull
        R property(@NonNull QProperty property);

        @NonNull
        R function(@NonNull FunctionCriterion function);

        @NonNull
        R function(Collection<FunctionCriterion> functions);

        @NonNull
        R operator(@NonNull CriterionOperator operator);

        @NonNull
        R typeHandler(@NonNull Class<? extends TypeHandler> typeHandler);

    }

}

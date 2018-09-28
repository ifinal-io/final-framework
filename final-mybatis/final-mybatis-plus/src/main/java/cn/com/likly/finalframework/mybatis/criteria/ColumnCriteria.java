package cn.com.likly.finalframework.mybatis.criteria;

import cn.com.likly.finalframework.mybatis.criteria.enums.AndOr;
import cn.com.likly.finalframework.mybatis.criteria.enums.CompareOperation;
import lombok.Data;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 14:31
 * @since 1.0
 */
@Data
public class ColumnCriteria {
    private final AndOr andOr;
    private final String column;
    private final CompareOperation operation;
    private final Object value;
    private final Object min;
    private final Object max;

    private ColumnCriteria(AndOr andOr, String column, CompareOperation operation, Object value, Object min, Object max) {
        this.andOr = andOr;
        this.column = column;
        this.operation = operation;
        this.value = value;
        this.min = min;
        this.max = max;
    }

    public static class Builder<T> {
        private final T target;
        private final AndOr andOr;
        private final String column;

        Builder(String column) {
            this(null, AndOr.AND, column);
        }

        Builder(T target, AndOr andOr, String column) {
            this.target = target;
            this.andOr = andOr;
            this.column = column;
        }

        public T equal(@NonNull Object value) {
            return build(CompareOperation.EQUAL, value, null, null);
        }

        public T notEqual(@NonNull Object value) {
            return build(CompareOperation.NOT_EQUAL, value, null, null);
        }

        public T greatThan(@NonNull Number value) {
            return build(CompareOperation.GREAT_THAN, value, null, null);
        }

        public T greanEqualThan(@NonNull Number value) {
            return build(CompareOperation.GREAT_EQUAL_THAN, value, null, null);
        }

        public T lessThan(@NonNull Number value) {
            return build(CompareOperation.LESS_THAN, value, null, null);
        }

        public T lessEqualThan(@NonNull Number value) {
            return build(CompareOperation.LESS_EQUAL_THAN, value, null, null);
        }

        public T in(@NonNull Object... value) {
            return build(CompareOperation.IN, Arrays.stream(value).collect(Collectors.toSet()), null, null);
        }

        public T notIn(@NonNull Object... value) {
            return build(CompareOperation.NOT_IN, Arrays.stream(value).collect(Collectors.toSet()), null, null);
        }

        public T in(@NonNull Collection value) {
            return build(CompareOperation.IN, value.stream().collect(Collectors.toSet()), null, null);
        }

        public T notIn(@NonNull Collection value) {
            return build(CompareOperation.NOT_IN, value.stream().collect(Collectors.toSet()), null, null);
        }

        public T like(@NonNull String value) {
            return build(CompareOperation.LIKE, value, null, null);
        }

        public T notLike(@NonNull String value) {
            return build(CompareOperation.NOT_LIKE, value, null, null);
        }


        public T isNull() {
            return build(CompareOperation.NULL, null, null, null);
        }

        public T notNull() {
            return build(CompareOperation.NOT_NULL, null, null, null);
        }


        public T between(@NonNull Object min, @NonNull Object max) {
            return build(CompareOperation.BETWEEN, null, min, max);
        }

        public T notBetween(@NonNull Object min, @NonNull Object max) {
            return build(CompareOperation.NOT_BETWEEN, null, min, max);
        }

        private T build(CompareOperation operation, Object value, Object min, Object max) {
            ColumnCriteria columnCriteria = new ColumnCriteria(andOr, column, operation, value, min, max);

            if (target instanceof WhereCriteria.Builder) {
                ((WhereCriteria.Builder) target).where(columnCriteria);
                return target;
            }

            if (target instanceof Criteria.Builder) {
                ((Criteria.Builder) target).where(andOr, columnCriteria);
                return target;
            }


            return (T) columnCriteria;
        }

    }
}

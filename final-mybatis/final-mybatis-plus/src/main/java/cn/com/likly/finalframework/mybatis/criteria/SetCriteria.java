package cn.com.likly.finalframework.mybatis.criteria;

import cn.com.likly.finalframework.mybatis.criteria.enums.SetOperation;
import lombok.Data;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 14:31
 * @since 1.0
 */
@Data
public class SetCriteria {
    private final String column;
    private final SetOperation operation;
    private final Object value;

    private SetCriteria(String column, SetOperation operation, Object value) {
        this.column = column;
        this.operation = operation;
        this.value = value;
    }

    public static class Builder<T> {
        private final T target;
        private final String column;

        public Builder(String column) {
            this(null, column);
        }

        public Builder(T target, String column) {
            this.target = target;
            this.column = column;
        }

        public T build(SetOperation operation, Object value) {
            final SetCriteria setCriteria = new SetCriteria(column, operation, value);

            if (target instanceof Criteria.Builder) {
                return target;
            }

            return (T) setCriteria;
        }
    }
}

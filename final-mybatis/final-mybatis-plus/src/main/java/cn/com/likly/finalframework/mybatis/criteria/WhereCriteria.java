package cn.com.likly.finalframework.mybatis.criteria;

import cn.com.likly.finalframework.mybatis.criteria.enums.AndOr;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 14:31
 * @since 1.0
 */
@Data
public class WhereCriteria {
    private final AndOr andOr;
    private final List<ColumnCriteria> columns;

    WhereCriteria(AndOr andOr, List<ColumnCriteria> columns) {
        this.andOr = andOr;
        this.columns = Collections.unmodifiableList(columns);
    }


    public static class Builder<T> {
        private final T target;
        private final AndOr andOr;
        private final List<ColumnCriteria> columns = new ArrayList<>();

        public Builder(AndOr andOr) {
            this(null, andOr);
        }

        public Builder(T target, AndOr andOr) {
            this.target = target;
            this.andOr = andOr;
        }

        public ColumnCriteria.Builder<WhereCriteria.Builder<T>> and(String column) {
            return where(AndOr.AND, column);
        }

        public ColumnCriteria.Builder<WhereCriteria.Builder<T>> or(String column) {
            return where(AndOr.OR, column);
        }

        WhereCriteria.Builder<WhereCriteria> where(ColumnCriteria columnCriteria) {
            columns.add(columnCriteria);
            return (Builder<WhereCriteria>) this;
        }

        ColumnCriteria.Builder<WhereCriteria.Builder<T>> where(AndOr andOr, String column) {
            return new ColumnCriteria.Builder<>(this, andOr, column);
        }

        public T build() {
            final WhereCriteria whereCriteria = new WhereCriteria(this.andOr, Collections.unmodifiableList(columns));

            if (target instanceof WhereCriteria) {

            }

            return (T) whereCriteria;
        }
    }
}

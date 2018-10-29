package cn.com.likly.finalframework.data.domain;

import cn.com.likly.finalframework.data.mapping.Property;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 13:36
 * @since 1.0
 */
public interface QProperty extends Property, Criteriable<Criteria>, Sortable<Sort> {
    @Override
    default Criteria eq(@NotNull Object value) {
        return Criteria.where(this).eq(value);
    }

    @Override
    default Criteria neq(@NotNull Object value) {
        return Criteria.where(this).neq(value);
    }

    @Override
    default Criteria gt(@NotNull Comparable value) {
        return Criteria.where(this).gt(value);
    }

    @Override
    default Criteria gte(@NotNull Comparable value) {
        return Criteria.where(this).gte(value);
    }

    @Override
    default Criteria lt(@NotNull Comparable value) {
        return Criteria.where(this).lt(value);
    }

    @Override
    default Criteria lte(@NotNull Comparable value) {
        return Criteria.where(this).lte(value);
    }

    @Override
    default Criteria in(@NotEmpty Object... values) {
        return Criteria.where(this).in(values);
    }

    @Override
    default Criteria in(@NotEmpty Collection<?> values) {
        return Criteria.where(this).in(values);
    }

    @Override
    default Criteria nin(@NotEmpty Object... values) {
        return Criteria.where(this).nin(values);
    }

    @Override
    default Criteria nin(@NotEmpty Collection<?> values) {
        return Criteria.where(this).nin(values);
    }

    @Override
    default Criteria startWith(@NotEmpty String value) {
        return Criteria.where(this).startWith(value);
    }

    @Override
    default Criteria notStartWith(@NotEmpty String value) {
        return Criteria.where(this).notStartWith(value);
    }

    @Override
    default Criteria endWith(@NotEmpty String value) {
        return Criteria.where(this).endWith(value);
    }

    @Override
    default Criteria notEndWith(@NotEmpty String value) {
        return Criteria.where(this).notEndWith(value);
    }

    @Override
    default Criteria contains(@NotEmpty String value) {
        return Criteria.where(this).contains(value);
    }

    @Override
    default Criteria notContains(@NotEmpty String value) {
        return Criteria.where(this).notContains(value);
    }

    @Override
    default Criteria like(@NotBlank String value) {
        return Criteria.where(this).like(value);
    }

    @Override
    default Criteria notLike(@NotBlank String value) {
        return Criteria.where(this).notLike(value);
    }

    @Override
    default Criteria before(@NotNull Date date) {
        return Criteria.where(this).before(date);
    }

    @Override
    default Criteria before(@NotNull long date) {
        return Criteria.where(this).before(date);
    }

    @Override
    default Criteria after(@NotNull Date date) {
        return Criteria.where(this).after(date);
    }

    @Override
    default Criteria after(@NotNull long date) {
        return Criteria.where(this).after(date);
    }

    @Override
    default Criteria dateBefore(@NotNull Date date) {
        return Criteria.where(this).dateBefore(date);
    }

    @Override
    default Criteria dateBefore(@NotNull long date) {
        return Criteria.where(this).dateBefore(date);
    }

    @Override
    default Criteria dateAfter(@NotNull Date date) {
        return Criteria.where(this).dateAfter(date);
    }

    @Override
    default Criteria dateAfter(@NotNull long date) {
        return Criteria.where(this).dateAfter(date);
    }

    @Override
    default <E extends Comparable> Criteria between(@NotNull E min, @NotNull E max) {
        return Criteria.where(this).between(min, max);
    }

    @Override
    default <E extends Comparable> Criteria notBetween(@NotNull E min, @NotNull E max) {
        return Criteria.where(this).notBetween(min, max);
    }

    @Override
    default Criteria dateBetween(@NotNull Date min, @NotNull Date max) {
        return Criteria.where(this).dateBetween(min, max);
    }

    @Override
    default Criteria notDateBetween(@NotNull Date min, @NotNull Date max) {
        return Criteria.where(this).dateBetween(min, max);
    }

    @Override
    default Criteria dateBetween(@NotNull long min, @NotNull long max) {
        return Criteria.where(this).notDateBetween(min, max);
    }

    @Override
    default Criteria notDateBetween(@NotNull long min, @NotNull long max) {
        return Criteria.where(this).notDateBetween(min, max);
    }

    @Override
    default Sort asc() {
        return Sort.asc(this);
    }

    @Override
    default Sort desc() {
        return Sort.desc(this);
    }
}

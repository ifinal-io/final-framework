package cn.com.likly.finalframework.data.query;

import cn.com.likly.finalframework.data.query.enums.AndOr;
import cn.com.likly.finalframework.data.query.enums.CriteriaOperation;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-25 11:33
 * @since 1.0
 */
@Getter
public class CriteriaSet implements Criteriable<Criteria> {
    private final Criteria criteria;
    private final QProperty property;
    private AndOr andOr;
    private CriteriaOperation operation;
    private Object value;
    private Object min;
    private Object max;

    CriteriaSet(Criteria criteria, QProperty property, AndOr andOr) {
        this.criteria = criteria;
        this.property = property;
        this.andOr = andOr;
    }

    public static CriteriaSet where(QProperty property, AndOr andOr) {
        return new CriteriaSet(null, property, andOr);
    }

    public static CriteriaSet where(QProperty property) {
        return where(property, AndOr.AND);
    }

    void setAndOr(AndOr andOr) {
        this.andOr = andOr;
    }

    @Override
    public Criteria eq(@NotNull Object value) {
        this.operation = CriteriaOperation.EQUAL;
        this.value = value;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria neq(@NotNull Object value) {
        this.operation = CriteriaOperation.NOT_EQUAL;
        this.value = value;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria gt(@NotNull Comparable value) {
        this.operation = CriteriaOperation.GREATER_THAN;
        this.value = value;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria gte(@NotNull Comparable value) {
        this.operation = CriteriaOperation.GREATER_EQUAL_THAN;
        this.value = value;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria lt(@NotNull Comparable value) {
        this.operation = CriteriaOperation.LESS_THAN;
        this.value = value;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria lte(@NotNull Comparable value) {
        this.operation = CriteriaOperation.LESS_EQUAL_THAN;
        this.value = value;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria in(@NotEmpty Object... values) {
        return in(Arrays.asList(values));
    }

    @Override
    public Criteria in(@NotEmpty Collection<?> values) {
        this.operation = CriteriaOperation.IN;
        this.value = new HashSet<>(values);
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria nin(@NotEmpty Object... values) {
        return nin(Arrays.asList(values));
    }

    @Override
    public Criteria nin(@NotEmpty Collection<?> values) {
        this.operation = CriteriaOperation.NOT_IN;
        this.value = new HashSet<>(values);
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria startWith(@NotEmpty String value) {
        this.operation = CriteriaOperation.START_WITH;
        this.value = value;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria notStartWith(@NotEmpty String value) {
        this.operation = CriteriaOperation.NOT_START_WITH;
        this.value = value;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria endWith(@NotEmpty String value) {
        this.operation = CriteriaOperation.END_WITH;
        this.value = value;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria notEndWith(@NotEmpty String value) {
        this.operation = CriteriaOperation.NOT_END_WITH;
        this.value = value;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria contains(@NotEmpty String value) {
        this.operation = CriteriaOperation.CONTAINS;
        this.value = value;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria notContains(@NotEmpty String value) {
        this.operation = CriteriaOperation.NOT_CONTAINS;
        this.value = value;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria like(@NotBlank String value) {
        this.operation = CriteriaOperation.LIKE;
        this.value = value;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria notLike(@NotBlank String value) {
        this.operation = CriteriaOperation.NOT_LIKE;
        this.value = value;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria before(@NotNull Date date) {
        this.operation = CriteriaOperation.BEFORE;
        this.value = date;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria before(@NotNull long date) {
        return before(new Date(date));
    }

    @Override
    public Criteria after(@NotNull Date date) {
        this.operation = CriteriaOperation.AFTER;
        this.value = date;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria after(@NotNull long date) {
        return after(new Date(date));
    }

    @Override
    public Criteria dateBefore(@NotNull Date date) {
        this.operation = CriteriaOperation.DATE_BEFORE;
        this.value = date;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria dateBefore(@NotNull long date) {
        return dateBefore(new Date(date));
    }

    @Override
    public Criteria dateAfter(@NotNull Date date) {
        this.operation = CriteriaOperation.DATE_AFTER;
        this.value = date;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria dateAfter(@NotNull long date) {
        return dateAfter(new Date(date));
    }

    @Override
    public <E extends Comparable> Criteria between(@NotNull E min, @NotNull E max) {
        this.operation = CriteriaOperation.BETWEEN;
        this.min = min;
        this.max = max;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public <E extends Comparable> Criteria notBetween(@NotNull E min, @NotNull E max) {
        this.operation = CriteriaOperation.NOT_BETWEEN;
        this.min = min;
        this.max = max;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria dateBetween(@NotNull Date min, @NotNull Date max) {
        this.operation = CriteriaOperation.DATE_BETWEEN;
        this.min = min;
        this.max = max;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria notDateBetween(@NotNull Date min, @NotNull Date max) {
        this.operation = CriteriaOperation.NOT_BETWEEN;
        this.min = min;
        this.max = max;
        return criteria == null ? new Criteria(this) : criteria.addCriteriaSet(this);
    }

    @Override
    public Criteria dateBetween(@NotNull long min, @NotNull long max) {
        return dateBetween(new Date(min), new Date(max));
    }

    @Override
    public Criteria notDateBetween(@NotNull long min, @NotNull long max) {
        return notDateBetween(new Date(min), new Date(max));
    }
}

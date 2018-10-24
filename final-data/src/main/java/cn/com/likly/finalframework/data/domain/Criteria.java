package cn.com.likly.finalframework.data.domain;

import cn.com.likly.finalframework.data.domain.enums.AndOr;
import cn.com.likly.finalframework.data.domain.enums.CriteriaOperation;
import cn.com.likly.finalframework.data.mapping.holder.PropertyHolder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-12 16:36
 * @since 1.0
 */
@Getter
@SuppressWarnings("unused")
public class Criteria implements CriteriaFunction<Criteria>, Iterable<Criteria>, Serializable {
    private static final long serialVersionUID = -7000622805428762640L;

    private PropertyHolder property;
    private AndOr andOr = AndOr.AND;
    private CriteriaOperation operation;
    private Object value;
    private Object min;
    private Object max;
    private List<Criteria> criteriaChain;

    private Criteria(PropertyHolder property) {
        this.property = property;
        criteriaChain = new LinkedList<>();
    }

    private Criteria(PropertyHolder property, AndOr andOr, CriteriaOperation operation, Object value, Object min, Object max) {
        this.property = property;
        this.andOr = andOr;
        this.operation = operation;
        this.value = value;
        this.min = min;
        this.max = max;
    }

    @SafeVarargs
    private Criteria(AndOr andOr, Criteria... criteria) {
        this.andOr = andOr;
        this.criteriaChain = Arrays.asList(criteria);
    }

    private Criteria(AndOr andOr, List<Criteria> criteriaChain) {
        this.andOr = andOr;
        this.criteriaChain = criteriaChain;
    }


    public static Criteria where(PropertyHolder key) {
        return new Criteria(key);
    }

    public static Criteria of(Criteria... criteria) {
        return of(Arrays.asList(criteria));
    }

    public static Criteria of(Collection<Criteria> criteria) {
        return of(AndOr.AND, new ArrayList<>(criteria));
    }

    public static Criteria of(AndOr andOr, Criteria... criteria) {
        return of(andOr, Arrays.asList(criteria));
    }

    public static Criteria of(AndOr andOr, Collection<Criteria> criteria) {
        criteria.forEach(it -> it.andOr = andOr);
        return new Criteria(andOr, new ArrayList<>(criteria));
    }

    public Criteria and(PropertyHolder key) {
        this.property = key;
        return this;
    }

    public Criteria and(Criteria... criteria) {
        return and(Arrays.asList(criteria));
    }

    public Criteria and(Collection<Criteria> criteria) {
        criteria.forEach(it -> it.andOr = AndOr.AND);
        criteriaChain.add(new Criteria(AndOr.AND, new ArrayList<>(criteria)));
        return this;
    }

    public Criteria or(Criteria... criteria) {
        return or(Arrays.asList(criteria));
    }

    public Criteria or(Collection<Criteria> criteria) {
        criteria.forEach(it -> it.andOr = AndOr.OR);
        criteriaChain.add(new Criteria(AndOr.OR, new ArrayList<>(criteria)));
        return this;
    }

    @Override
    public Criteria is(Object value) {
        this.criteriaChain.add(new Criteria(property, andOr, CriteriaOperation.EQUAL, value, null, null));
        return this;
    }

    @Override
    public Criteria ne(Object value) {
        this.criteriaChain.add(new Criteria(property, andOr, CriteriaOperation.NOT_EQUAL, value, null, null));
        return this;
    }

    @Override
    public Criteria gt(@NotNull Object value) {
        this.criteriaChain.add(new Criteria(property, andOr, CriteriaOperation.GREATER_THAN, value, null, null));
        return this;
    }

    @Override
    public Criteria gte(@NotNull Object value) {
        this.criteriaChain.add(new Criteria(property, andOr, CriteriaOperation.GREATER_EQUAL_THAN, value, null, null));
        return this;
    }

    @Override
    public Criteria lt(@NotNull Object value) {
        this.criteriaChain.add(new Criteria(property, andOr, CriteriaOperation.LESS_THAN, value, null, null));
        return this;
    }

    @Override
    public Criteria lte(@NotNull Object value) {
        this.criteriaChain.add(new Criteria(property, andOr, CriteriaOperation.LESS_EQUAL_THAN, value, null, null));
        return this;
    }

    @Override
    public Criteria in(@NotEmpty Object... values) {
        return in(Arrays.asList(values));
    }

    @Override
    public Criteria in(@NotEmpty Collection<?> values) {
        Set<?> set = new HashSet<>(values);
        this.criteriaChain.add(new Criteria(property, andOr, CriteriaOperation.IN, set, null, null));
        return this;
    }

    @Override
    public Criteria nin(@NotEmpty Object... values) {
        return nin(Arrays.asList(values));
    }

    @Override
    public Criteria nin(@NotEmpty Collection<?> values) {
        Set<?> set = new HashSet<>(values);
        this.criteriaChain.add(new Criteria(property, andOr, CriteriaOperation.NOT_IN, set, null, null));
        return this;
    }

    @Override
    public Criteria like(@NotBlank String value) {
        this.criteriaChain.add(new Criteria(property, andOr, CriteriaOperation.LIKE, value, null, null));
        return this;
    }

    @Override
    public Criteria nlike(@NotBlank String value) {
        this.criteriaChain.add(new Criteria(property, andOr, CriteriaOperation.NOT_LIKE, value, null, null));
        return this;
    }

    @Override
    public <E extends Comparable> Criteria between(@NotNull E min, @NotNull E max) {
        this.criteriaChain.add(new Criteria(property, andOr, CriteriaOperation.BETWEEN, null, min, max));
        return this;
    }

    @Override
    public <E extends Comparable> Criteria notBetween(@NotNull E min, @NotNull E max) {
        this.criteriaChain.add(new Criteria(property, andOr, CriteriaOperation.NOT_BETWEEN, null, min, max));
        return this;
    }

    @Override
    public Iterator<Criteria> iterator() {
        return criteriaChain.iterator();
    }

    public Stream<Criteria> stream() {

        if (criteriaChain.isEmpty()) return Stream.of(this);

        return criteriaChain.stream();
    }
}

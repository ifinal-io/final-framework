package cn.com.likly.finalframework.data.domain;

import cn.com.likly.finalframework.data.domain.enums.AndOr;
import cn.com.likly.finalframework.data.domain.enums.CriteriaOperation;
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
public class Criteria<T> implements CriteriaFunction<Criteria<T>>, Iterable<Criteria<T>>, Serializable {
    private static final long serialVersionUID = -7000622805428762640L;

    private T property;
    private AndOr andOr = AndOr.AND;
    private CriteriaOperation operation;
    private Object value;
    private Object min;
    private Object max;
    private List<Criteria<T>> criteriaChain;

    private Criteria(T property) {
        this.property = property;
        criteriaChain = new LinkedList<>();
    }

    private Criteria(T property, AndOr andOr, CriteriaOperation operation, Object value, Object min, Object max) {
        this.property = property;
        this.andOr = andOr;
        this.operation = operation;
        this.value = value;
        this.min = min;
        this.max = max;
    }

    @SafeVarargs
    private Criteria(AndOr andOr, Criteria<T>... criteria) {
        this.andOr = andOr;
        this.criteriaChain = Arrays.asList(criteria);
    }

    private Criteria(AndOr andOr, List<Criteria<T>> criteriaChain) {
        this.andOr = andOr;
        this.criteriaChain = criteriaChain;
    }


    public static <T> Criteria<T> where(T key) {
        return new Criteria<>(key);
    }

    public static <T> Criteria<T> of(Criteria<T>... criteria) {
        return of(Arrays.asList(criteria));
    }

    public static <T> Criteria<T> of(Collection<Criteria<T>> criteria) {
        return of(AndOr.AND, new ArrayList<>(criteria));
    }

    public static <T> Criteria<T> of(AndOr andOr, Criteria<T>... criteria) {
        return of(andOr, Arrays.asList(criteria));
    }

    public static <T> Criteria<T> of(AndOr andOr, Collection<Criteria<T>> criteria) {
        criteria.forEach(it -> it.andOr = andOr);
        return new Criteria<>(andOr, new ArrayList<>(criteria));
    }

    public Criteria<T> and(T key) {
        this.property = key;
        return this;
    }

    public Criteria<T> and(Criteria<T>... criteria) {
        return and(Arrays.asList(criteria));
    }

    public Criteria<T> and(Collection<Criteria<T>> criteria) {
        criteria.forEach(it -> it.andOr = AndOr.AND);
        criteriaChain.add(new Criteria<>(AndOr.AND, new ArrayList<>(criteria)));
        return this;
    }

    public Criteria<T> or(Criteria<T>... criteria) {
        return or(Arrays.asList(criteria));
    }

    public Criteria<T> or(Collection<Criteria<T>> criteria) {
        criteria.forEach(it -> it.andOr = AndOr.OR);
        criteriaChain.add(new Criteria<>(AndOr.OR, new ArrayList<>(criteria)));
        return this;
    }

    @Override
    public Criteria<T> is(Object value) {
        this.criteriaChain.add(new Criteria<>(property, andOr, CriteriaOperation.EQUAL, value, null, null));
        return this;
    }

    @Override
    public Criteria<T> ne(Object value) {
        this.criteriaChain.add(new Criteria<>(property, andOr, CriteriaOperation.NOT_EQUAL, value, null, null));
        return this;
    }

    @Override
    public Criteria<T> gt(@NotNull Object value) {
        this.criteriaChain.add(new Criteria<>(property, andOr, CriteriaOperation.GREATER_THAN, value, null, null));
        return this;
    }

    @Override
    public Criteria<T> gte(@NotNull Object value) {
        this.criteriaChain.add(new Criteria<>(property, andOr, CriteriaOperation.GREATER_EQUAL_THAN, value, null, null));
        return this;
    }

    @Override
    public Criteria<T> lt(@NotNull Object value) {
        this.criteriaChain.add(new Criteria<>(property, andOr, CriteriaOperation.LESS_THAN, value, null, null));
        return this;
    }

    @Override
    public Criteria<T> lte(@NotNull Object value) {
        this.criteriaChain.add(new Criteria<>(property, andOr, CriteriaOperation.LESS_EQUAL_THAN, value, null, null));
        return this;
    }

    @Override
    public Criteria<T> in(@NotEmpty Object... values) {
        return in(Arrays.asList(values));
    }

    @Override
    public Criteria<T> in(@NotEmpty Collection<?> values) {
        Set<?> set = new HashSet<>(values);
        this.criteriaChain.add(new Criteria<>(property, andOr, CriteriaOperation.IN, set, null, null));
        return this;
    }

    @Override
    public Criteria<T> nin(@NotEmpty Object... values) {
        return nin(Arrays.asList(values));
    }

    @Override
    public Criteria<T> nin(@NotEmpty Collection<?> values) {
        Set<?> set = new HashSet<>(values);
        this.criteriaChain.add(new Criteria<>(property, andOr, CriteriaOperation.NOT_IN, set, null, null));
        return this;
    }

    @Override
    public Criteria<T> like(@NotBlank String value) {
        this.criteriaChain.add(new Criteria<>(property, andOr, CriteriaOperation.LIKE, value, null, null));
        return this;
    }

    @Override
    public Criteria<T> nlike(@NotBlank String value) {
        this.criteriaChain.add(new Criteria<>(property, andOr, CriteriaOperation.NOT_LIKE, value, null, null));
        return this;
    }

    @Override
    public <E extends Comparable> Criteria<T> between(@NotNull E min, @NotNull E max) {
        this.criteriaChain.add(new Criteria<>(property, andOr, CriteriaOperation.BETWEEN, null, min, max));
        return this;
    }

    @Override
    public <E extends Comparable> Criteria<T> notBetween(@NotNull E min, @NotNull E max) {
        this.criteriaChain.add(new Criteria<>(property, andOr, CriteriaOperation.NOT_BETWEEN, null, min, max));
        return this;
    }

    @Override
    public Iterator<Criteria<T>> iterator() {
        return criteriaChain.iterator();
    }

    public Stream<Criteria<T>> stream() {

        if (criteriaChain.isEmpty()) return Stream.of(this);

        return criteriaChain.stream();
    }
}

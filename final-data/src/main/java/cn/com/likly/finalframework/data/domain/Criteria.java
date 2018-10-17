package cn.com.likly.finalframework.data.domain;

import cn.com.likly.finalframework.data.domain.enums.AndOr;
import cn.com.likly.finalframework.data.domain.enums.CriteriaOperation;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-12 16:36
 * @since 1.0
 */
@Getter
public class Criteria implements CriteriaFunction<Criteria>, Iterable<Criteria>, Serializable {
    private static final long serialVersionUID = -7000622805428762640L;

    private String property;
    private AndOr andOr = AndOr.AND;
    private CriteriaOperation operation;
    private Object value;
    private Object min;
    private Object max;
    private List<Criteria> criteriaChain;

    public Criteria() {
        this.criteriaChain = new LinkedList<>();
    }

    public Criteria(String property) {
        this.property = property;
        criteriaChain = new LinkedList<>();
    }

    private Criteria(String property, AndOr andOr, CriteriaOperation operation, Object value, Object min, Object max) {
        this.property = property;
        this.andOr = andOr;
        this.operation = operation;
        this.value = value;
        this.min = min;
        this.max = max;
    }

    private Criteria(AndOr andOr, Criteria... criteria) {
        this.andOr = andOr;
        this.criteriaChain = Arrays.asList(criteria);
    }

    private Criteria(AndOr andOr, List<Criteria> criteriaChain) {
        this.andOr = andOr;
        this.criteriaChain = criteriaChain;
    }


    public static Criteria where(String key) {
        return new Criteria(key);
    }

    public static Criteria of(AndOr andOr, Criteria... criteria) {
        return of(andOr, Arrays.asList(criteria));
    }

    public static Criteria of(AndOr andOr, Collection<Criteria> criteria) {
        criteria.forEach(it -> it.andOr = andOr);
        return new Criteria(andOr, new ArrayList<>(criteria));
    }

    public static void main(String[] args) {
        Criteria criteria = Criteria.where("id").is(1)
                .and(Criteria.of(AndOr.OR, Criteria.where("age").is(13), Criteria.where("sex").is(1)));
        System.out.println(criteria);
    }

    public Criteria and(String key) {
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
    public String toString() {

        if (isLeaf()) {
            switch (operation) {
                case EQUAL:
                    return value instanceof String ? String.format("%s == '%s'", property, value) : String.format("%s == %s", property, value.toString());
                case NOT_EQUAL:
                    return value instanceof String ? String.format("%s != '%s'", property, value) : String.format("%s != %s", property, value.toString());
                case GREATER_THAN:
                    return value instanceof String ? String.format("%s > '%s'", property, value) : String.format("%s > %s", property, value.toString());
                case GREATER_EQUAL_THAN:
                    return value instanceof String ? String.format("%s >= '%s'", property, value) : String.format("%s >= %s", property, value.toString());
                case LESS_THAN:
                    return value instanceof String ? String.format("%s < '%s'", property, value) : String.format("%s < %s", property, value.toString());
                case LESS_EQUAL_THAN:
                    return value instanceof String ? String.format("%s <= '%s'", property, value) : String.format("%s <= %s", property, value.toString());
                case IN:
                    return String.format("%s IN %s", property, buildInString((Collection<?>) value));
                case NOT_IN:
                    return String.format("%s NOT IN %s", property, buildInString((Collection<?>) value));
                case LIKE:
                    return String.format("%s LIKE '%%%s%%'", property, value.toString());
                case NOT_LIKE:
                    return String.format("%s NOT LIKE '%%%s%%'", property, value.toString());
                case NULL:
                    return String.format("%s IS NULL", property);
                case NOT_NULL:
                    return String.format("%s IS NOT NULL", property);
                case BETWEEN:
                    return value instanceof String ? String.format("%s BETWEEN '%s' AND '%s'", property, min, max) : String.format("%s BETWEEN %s AND %s", property, min, max);
                case NOT_BETWEEN:
                    return value instanceof String ? String.format("%s NOT BETWEEN '%s' AND '%s'", property, min, max) : String.format("%s BETWEEN %s AND %s", property, min, max);
                default:
                    return "";
            }
        } else {
            return join();
        }

    }

    private String buildInString(Collection<?> collection) {
        final StringBuilder sb = new StringBuilder();

        sb.append("(");
        int i = 0;
        for (Object item : collection) {
            if (i++ != 0) {
                sb.append(",");
            }

            sb.append(item instanceof String ? String.format("'%s'", item) : item);
        }
        sb.append(")");


        return sb.toString();
    }

    private String join() {

        final StringBuilder sb = new StringBuilder();


        for (int i = 0; i < criteriaChain.size(); i++) {
            Criteria criteria = criteriaChain.get(i);

            if (i != 0) {
                sb.append(" ").append(criteria.andOr.name()).append(" ");
            }

            if (criteria.getCount() > 1) {
                sb.append("(");
            }

            sb.append(criteriaChain.get(i).toString());

            if (criteria.getCount() > 1) {
                sb.append(")");
            }
        }


        return sb.toString();
    }

    private boolean isLeaf() {
        return criteriaChain == null || criteriaChain.isEmpty();
    }

    private int getCount() {
        return criteriaChain == null ? 0 : criteriaChain.size();
    }

    @Override
    public Iterator<Criteria> iterator() {
        return criteriaChain.iterator();
    }
}

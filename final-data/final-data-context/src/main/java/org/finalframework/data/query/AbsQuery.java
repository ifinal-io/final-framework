package org.finalframework.data.query;


import org.finalframework.data.query.enums.AndOr;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-22 09:47:02
 * @since 1.0
 */
public abstract class AbsQuery<T extends AbsCriteria, Q extends AbsQuery> implements Queryable {
    private final Query query;

    public AbsQuery() {
        this.query = new Query();
    }

    public Q page(Integer page, Integer size) {
        this.query.page(page, size);
        return (Q) this;
    }

    public Q addCriteria(T criteria) {
        this.query.where(criteria.convert());
        return (Q) this;
    }

    public T andCriteria() {
        T criteria = createAndCriteria();
        this.query.where(criteria.convert());
        return criteria;
    }

    public T orCriteria() {
        T criteria = createOrCriteria();
        this.query.where(criteria.convert());
        return criteria;
    }

    public T createAndCriteria() {
        return createCriteria(AndOr.AND);
    }

    public T createOrCriteria() {
        return createCriteria(AndOr.OR);
    }

    protected abstract T createCriteria(AndOr andOr);


    protected void addOrder(Order order) {
        this.query.sort(order);
    }

    protected void addGroup(QProperty property) {
        this.query.group(property);
    }

    public Q limit(long offset, long limit) {
        this.query.limit(offset, limit);
        return (Q) this;
    }

    public Q limit(long limit) {
        this.query.limit(limit);
        return (Q) this;
    }


    @Override
    public Query convert() {
        return query;
    }
}


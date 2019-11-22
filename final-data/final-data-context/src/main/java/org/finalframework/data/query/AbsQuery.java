package org.finalframework.data.query;


/**
 * @author likly
 * @version 1.0
 * @date 2019-11-22 09:47:02
 * @since 1.0
 */
public abstract class AbsQuery implements Queryable {
    private final Query query;

    public AbsQuery() {
        this.query = new Query();
    }

    protected void where(Criterion criterion) {
        query.where(criterion);
    }

    @Override
    public Query convert() {
        return query;
    }
}


/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.data.query;


import org.finalframework.data.query.enums.AndOr;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-22 09:47:02
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public abstract class AbsQuery<T extends AbsCriteria, Q extends AbsQuery> implements Queryable {
    private final Query query;

    public AbsQuery() {
        this.query = new Query().page(1, 20);
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


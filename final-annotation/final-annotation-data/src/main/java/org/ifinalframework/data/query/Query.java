/*
 * Copyright 2020-2023 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ifinalframework.data.query;

import org.ifinalframework.core.IQuery;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.Collection;

/**
 * Class can be used to build a {@linkplain IQuery query} instance from java.
 *
 * <pre class="code">
 *      Query query = new Query.page(page,size)
 *          .where(QProperty.eq.("name"));
 * </pre>
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class Query extends PageQuery {

    public Query() {
        super.setPage(null);
        super.setSize(null);
    }

    /**
     * set query page and size
     *
     * @param page page
     * @param size size
     * @return query
     */
    public Query page(Integer page, Integer size) {
        return page(page).size(size);
    }

    /**
     * set query page
     *
     * @param page page
     * @return query
     */
    public Query page(Integer page) {
        setPage(page);
        return this;
    }

    /**
     * set query page size
     *
     * @param size size
     * @return query
     */
    public Query size(Integer size) {
        setSize(size);
        return this;
    }

    /**
     * count
     *
     * @param count count
     * @return query
     */
    public Query count(Boolean count) {
        setCount(count);
        return this;
    }

    /**
     * where
     *
     * @param criteria criteria
     * @return query
     */
    @Override
    public Query where(@NonNull Criterion... criteria) {
        return where(Arrays.asList(criteria));
    }

    /**
     * where
     *
     * @param criteria criteria
     * @return query
     */
    @Override
    public Query where(@NonNull Collection<Criterion> criteria) {
        return where(AndOr.AND, criteria);
    }

    @Override
    public Query where(AndOr andOr, @NonNull Collection<Criterion> criteria) {
        super.where(andOr, criteria);
        return this;
    }

    /**
     * @param properties properties
     * @return query
     */
    public Query group(QProperty<?>... properties) {
        return group(Arrays.asList(properties));
    }

    /**
     * @param properties properties
     * @return query
     */
    public Query group(Collection<QProperty<?>> properties) {
        properties.forEach(it -> this.addGroup(it.getColumn()));
        return this;
    }

    /**
     * @param columns columns
     * @return query
     */
    public Query group(String... columns) {
        for (String column : columns) {
            addGroup(column);
        }
        return this;
    }

    /**
     * @param orders orders
     * @return query
     */
    public Query sort(@NonNull Order... orders) {
        return sort(Arrays.asList(orders));
    }

    /**
     * @param orders orders
     * @return query
     */
    public Query sort(@NonNull Collection<Order> orders) {

        for (Order order : orders) {
            this.sort(order.getColumn(), order.getDirection());
        }

        return this;
    }

    /**
     * sort
     *
     * @param direction  direction
     * @param properties properties
     * @return query
     */
    public Query sort(@NonNull Direction direction, @NonNull QProperty<?>... properties) {

        for (QProperty<?> property : properties) {
            this.sort(property.getColumn(), direction);
        }

        return this;
    }

    /**
     * ase
     *
     * @param properties properties
     * @return query
     */
    public Query asc(@NonNull QProperty<?>... properties) {
        return sort(Direction.ASC, properties);
    }

    /**
     * desc
     *
     * @param properties properties
     * @return query
     */
    public Query desc(@NonNull QProperty<?>... properties) {
        return sort(Direction.DESC, properties);
    }

    /**
     * sort
     *
     * @param column    column
     * @param direction direction
     */
    private void sort(String column, Direction direction) {
        this.sort(String.format("%s %s", column, direction.name()));
    }

    /**
     * @param order order
     */
    private void sort(String order) {
        addOrder(order);
    }

    /**
     * @param offset offset
     * @param limit  limit
     * @return query
     */
    public Query limit(long offset, long limit) {
        setOffset(offset);
        setLimit(limit);
        return this;
    }

    /**
     * @param limit limit
     * @return query
     */
    public Query limit(long limit) {
        setOffset(null);
        setLimit(limit);
        return this;
    }

}

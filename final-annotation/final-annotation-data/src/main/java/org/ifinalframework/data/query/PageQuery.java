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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.ifinalframework.core.Groupable;
import org.ifinalframework.core.IQuery;
import org.ifinalframework.core.Limitable;
import org.ifinalframework.core.Orderable;
import org.ifinalframework.core.Pageable;
import org.ifinalframework.core.Viewable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@linkplain IQuery query} with {@link Pageable}, {@link Orderable},{@link Limitable} and {@link Groupable}.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Setter
@Getter
@ToString
public class PageQuery implements IQuery, Pageable, Groupable, Orderable, Limitable, Viewable, Serializable {

    private static final long serialVersionUID = 4813020012879522797L;

    /**
     * 默认分页页码
     */
    private static final Integer DEFAULT_PAGE = 1;

    /**
     * 默认分页容量
     */
    private static final Integer DEFAULT_SIZE = 20;

    @Getter
    private final Criteria criteria = new Criteria();

    /**
     * return the page index number for pageable, start from {@code 1}, could be null.
     */
    @Nullable
    @Min(1)
    private Integer page = DEFAULT_PAGE;

    /**
     * return the page size for pageable, min is {@code 1}, could be null.
     */
    @Nullable
    @Max(100000)
    private Integer size = DEFAULT_SIZE;

    /**
     * return {@code true} when need count query otherwise {@code false}.
     */
    @Nullable
    private Boolean count = Boolean.TRUE;

    @Nullable
    private List<String> groups;

    @Nullable
    private List<String> orders;

    @Nullable
    private Long offset;

    @Nullable
    private Long limit;

    private Class<?> view;

    public void setPage(final Integer page) {
        if (Objects.nonNull(page) && page < DEFAULT_PAGE) {
            this.page = DEFAULT_PAGE;
        } else {
            this.page = page;
        }
    }

    /**
     * where
     *
     * @param criteria criteria
     * @return query
     */
    public PageQuery where(@NonNull Criterion... criteria) {
        return where(Arrays.asList(criteria));
    }

    /**
     * where
     *
     * @param criteria criteria
     * @return query
     */
    public PageQuery where(@NonNull Collection<Criterion> criteria) {
        return where(AndOr.AND, criteria);
    }

    public PageQuery where(AndOr andOr, @NonNull Collection<Criterion> criteria) {
        if (AndOr.AND == andOr) {
            this.criteria.addAll(criteria);
        } else {
            this.criteria.add(Criteria.or(criteria));
        }
        return this;
    }


    /**
     * add order to {@link #orders}
     *
     * @param order order
     * @return page query
     * @since 1.2.2
     */
    public PageQuery addOrder(@NonNull String order) {
        if (orders == null) {
            orders = new LinkedList<>();
        }
        orders.add(order);
        return this;
    }

    /**
     * add group to {@link #groups}
     *
     * @param group group
     * @return page query
     * @since 1.2.2
     */
    public PageQuery addGroup(@NonNull String group) {
        if (Objects.isNull(groups)) {
            groups = new LinkedList<>();
        }
        groups.add(group);
        return this;
    }

}

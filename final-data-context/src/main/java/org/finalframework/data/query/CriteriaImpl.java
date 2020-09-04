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

import org.finalframework.data.annotation.query.AndOr;
import org.finalframework.data.query.criterion.Criterion;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-18 16:03:52
 * @since 1.0
 */
public class CriteriaImpl implements Criteria {
    private final AndOr andOr;
    private final Collection<Criterion> criteria;

    public CriteriaImpl() {
        this(AndOr.AND, new ArrayList<>());
    }

    protected CriteriaImpl(AndOr andOr, Collection<Criterion> criterion) {
        this.andOr = andOr;
        this.criteria = criterion;
    }

    @Override
    public AndOr andOr() {
        return andOr;
    }

    public AndOr getAndOr() {
        return andOr;
    }

    @Override
    public Criteria add(Collection<Criterion> criterion) {
        this.criteria.addAll(criterion);
        return this;
    }

    @Override
    public Criteria and(Criteria... criteria) {
        return andOr(AndOr.AND, criteria);
    }


    @Override
    public Criteria or(Criteria... criteria) {
        return andOr(AndOr.OR, criteria);
    }

    private Criteria andOr(AndOr andOr, Criteria... criteria) {
        List<Criterion> list = new ArrayList<>();
        list.add(this);
        list.addAll(Arrays.asList(criteria));
        return new CriteriaImpl(andOr, list);
    }

    @Override
    public Stream<Criterion> stream() {
        return criteria.stream();
    }

    @Override
    public Iterator<Criterion> iterator() {
        return criteria.iterator();
    }


}

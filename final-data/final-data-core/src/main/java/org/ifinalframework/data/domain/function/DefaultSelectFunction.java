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

package org.ifinalframework.data.domain.function;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import org.ifinalframework.core.IEntity;
import org.ifinalframework.core.INode;
import org.ifinalframework.core.IQuery;
import org.ifinalframework.data.query.CriterionTarget;
import org.ifinalframework.data.query.Query;
import org.ifinalframework.data.repository.Repository;
import org.ifinalframework.data.spi.SelectFunction;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * DefaultSelectFunction.
 *
 * @author iimik
 * @version 1.5.1
 * @see DefaultSelectOneFunction
 * @since 1.5.1
 */
@RequiredArgsConstructor
public class DefaultSelectFunction<K extends Serializable, T extends IEntity<K>, P, U> implements SelectFunction<P, U, List<T>> {
    private final Repository<K, T> repository;
    private final Class<?> view;

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public List<T> select(@NonNull P param, @NonNull U user) {
        final List<T> list = firstSelect(param, user);

        if (!CollectionUtils.isEmpty(list) && INode.class.isAssignableFrom(list.get(0).getClass())) {
            processTree(list);
        }

        return list;
    }

    private List<T> firstSelect(@NonNull P param, @NonNull U user) {
        if (param instanceof IQuery) {
            return repository.select(view, (IQuery) param);
        } else if (param instanceof Collection) {
            return repository.select(view, (Collection<K>) param);
        } else {
            return repository.select(view, (K) param);
        }
    }

    private void processTree(List<T> list) {
        if (!CollectionUtils.isEmpty(list)) {
            for (T t : list) {
                List<T> children = processTree(t.getId());
                ((INode<K, T>) t).setChildren(children);
            }
        }
    }

    private List<T> processTree(K parentId) {
        List<T> children = repository.select(view, new Query().where(CriterionTarget.from("parent_id").eq(parentId)));
        if (!CollectionUtils.isEmpty(children)) {
            processTree(children);
        }
        return children;
    }
}

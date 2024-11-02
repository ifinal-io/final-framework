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

package org.ifinalframework.data.domain;

import org.springframework.util.CollectionUtils;

import org.ifinalframework.context.exception.NotFoundException;
import org.ifinalframework.core.IEntity;
import org.ifinalframework.core.IQuery;
import org.ifinalframework.core.IUser;
import org.ifinalframework.data.domain.action.DeleteAction;
import org.ifinalframework.data.domain.action.UpdateAction;
import org.ifinalframework.data.repository.Repository;
import org.ifinalframework.data.spi.AfterConsumer;
import org.ifinalframework.data.spi.BiAfterThrowingConsumer;
import org.ifinalframework.data.spi.BiAfterReturningConsumer;
import org.ifinalframework.data.spi.BiConsumer;
import org.ifinalframework.data.spi.BiValidator;
import org.ifinalframework.data.spi.Consumer;
import org.ifinalframework.data.spi.Function;
import org.ifinalframework.data.spi.QueryConsumer;
import org.ifinalframework.data.spi.SpiAction;
import org.ifinalframework.data.spi.UpdateConsumer;
import org.ifinalframework.json.Json;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * AbsUpdateDomainAction.
 *
 * @author iimik
 * @version 1.5.0
 * @since 1.5.0
 */
@Setter
@RequiredArgsConstructor
public abstract class AbsUpdateDeleteDomainActionDispatcher<K extends Serializable, T extends IEntity<K>, P1, P2, V, U extends IUser<?>>
        extends AbsDomainAction
        implements DomainActionDispatcher<P1, V, U>, BiDomainActionDispatcher<P1, P2, V, U>,
        UpdateAction<P1, P2, V, U, Object>, DeleteAction<P1, U, Object> {
    private final SpiAction spiAction;
    private final Repository<K, T> repository;

    private QueryConsumer<P1, U> preQueryConsumer;

    private BiValidator<T, V, U> preUpdateValidator;
    private Consumer<T, U> preConsumer;
    private UpdateConsumer<T, V, U> preUpdateConsumer;
    private UpdateConsumer<T, V, U> postUpdateConsumer;
    private Consumer<T, U> postConsumer;

    private BiConsumer<T, P1, U> postQueryConsumer;
    private Function<Integer, P1, U> postQueryFunction;
    private BiAfterThrowingConsumer<T, P1, U> biAfterThrowingConsumer;
    private BiAfterReturningConsumer<T, P1, U> biAfterReturningConsumer;
    private AfterConsumer<T, P1, V, Integer, U> afterConsumer;


    @Override
    public Object update(String property, P1 param1, P2 param2, V value, U user) {
        return dispatch(property, param1, param2, value, user);
    }

    @Override
    public Object delete(P1 param, U user) {
        return dispatch(param, null, user);
    }

    @Override
    public Object dispatch(P1 param, V value, U user) {
        return dispatch(param, null, value, user);
    }

    @Override
    public Object dispatch(String property, P1 param1, P2 param2, V value, U user) {

        Integer result = null;
        List<T> list = null;
        Throwable throwable = null;
        try {

            if (Objects.nonNull(preQueryConsumer)) {
                preQueryConsumer.accept(spiAction, param1, user);
            }

            list = doActionPrepare(param1, param2, value, user);

            if (Objects.nonNull(param1) && CollectionUtils.isEmpty(list)) {
                throw new NotFoundException("not found target entities: {}", Json.toJson(param1));
            }

            if (Objects.nonNull(preUpdateValidator)) {
                preUpdateValidator.validate(list, value, user);
            }

            if (Objects.nonNull(preConsumer)) {
                preConsumer.accept(spiAction, SpiAction.Advice.PRE, list, user);
            }

            if (Objects.nonNull(preUpdateConsumer)) {
                preUpdateConsumer.accept(spiAction, SpiAction.Advice.PRE, list, value, user);
            }

            result = doInterAction(list, property, param1, param2, value, user);

            if (Objects.nonNull(postUpdateConsumer)) {
                postUpdateConsumer.accept(spiAction, SpiAction.Advice.POST, list, value, user);
            }

            if (Objects.nonNull(postConsumer)) {
                postConsumer.accept(spiAction, SpiAction.Advice.POST, list, user);
            }


            if (Objects.nonNull(postQueryConsumer)) {
                postQueryConsumer.accept(spiAction, SpiAction.Advice.POST, list, param1, user);
            }

            if (Objects.nonNull(postQueryFunction)) {
                return postQueryFunction.map(result, param1, user);
            }

            return result;
        } catch (Exception e) {
            throwable = e;
            if (Objects.nonNull(biAfterThrowingConsumer)) {
                biAfterThrowingConsumer.accept(spiAction, list, param1, user, e);
            }
            throw e;
        } finally {
            if (Objects.nonNull(biAfterReturningConsumer)) {
                biAfterReturningConsumer.accept(spiAction, list, param1, user, throwable);
            }
            if (Objects.nonNull(afterConsumer)) {
                afterConsumer.accept(spiAction, list, param1, value, result, user, throwable);
            }
        }
    }

    protected abstract Integer doInterAction(List<T> entities, String property, P1 query, P2 p2, V value, U user);

    protected List<T> doActionPrepare(P1 query, P2 param2, V value, U user) {

        if (Objects.isNull(query)) {
            return Collections.emptyList();
        }

        if (query instanceof IQuery) {
            return repository.select((IQuery) query);
        } else if (query instanceof Collection<?> ids) {
            return repository.select((Collection<K>) ids);
        } else {
            return repository.select((K) query);
        }
    }

}

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

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import org.ifinalframework.core.IEntity;
import org.ifinalframework.core.IEnum;
import org.ifinalframework.core.IQuery;
import org.ifinalframework.core.IUser;
import org.ifinalframework.data.annotation.YN;
import org.ifinalframework.data.domain.model.AuditValue;
import org.ifinalframework.data.domain.model.SortValue;
import org.ifinalframework.data.spi.BiAfterReturningConsumer;
import org.ifinalframework.data.spi.BiValidator;
import org.ifinalframework.data.spi.Filter;

import jakarta.validation.Valid;

import java.io.Serializable;
import java.util.List;

/**
 * DomainService.
 *
 * @author iimik
 * @version 1.4.3
 * @since 1.4.3
 */
@Validated
public interface DomainService<K extends Serializable, T extends IEntity<K>, U extends IUser<?>> {

    @NonNull
    Class<T> entityClass();

    @Nullable
    Class<?> domainEntityClass(Class<?> prefix);

    @NonNull
    Class<? extends IQuery> domainQueryClass(Class<?> prefix);

    Object export(@NonNull @Valid IQuery query, @NonNull U user);


    /**
     * @param entity the entities to create.
     * @param user   the current user.
     * @return created rows.
     * @see org.ifinalframework.core.IView.Create
     * @see Filter
     * @see org.ifinalframework.data.spi.Consumer
     * @see org.ifinalframework.data.spi.AfterThrowingConsumer
     * @see org.ifinalframework.data.spi.AfterReturningConsumer
     */
    Object create(@NonNull @Valid Object entity, @NonNull U user);

    /**
     * @param query the query of list.
     * @param user  the current user.
     * @return entity list of matches query.
     * @see org.ifinalframework.core.IView.List
     * @see org.ifinalframework.data.spi.QueryConsumer
     * @see org.ifinalframework.data.spi.BiConsumer
     * @see BiAfterReturningConsumer
     * @see org.ifinalframework.data.spi.Function
     * @see org.ifinalframework.data.spi.AfterThrowingConsumer
     * @see org.ifinalframework.data.spi.AfterReturningConsumer
     */
    Object list(@NonNull @Valid IQuery query, @NonNull U user);

    /**
     * @param query the query of detail.
     * @param user  the current user.
     * @return the detail entity of matches detail query.
     * @see org.ifinalframework.core.IView.Detail
     * @see org.ifinalframework.data.spi.QueryConsumer
     * @see org.ifinalframework.data.spi.BiConsumer
     * @see org.ifinalframework.data.spi.Consumer
     */
    Object detail(@NonNull @Valid IQuery query, @NonNull U user);

    /**
     * @param id   the id of entity.
     * @param user the current user.
     * @return the detail entity of {@code id}.
     * @see org.ifinalframework.core.IView.Detail
     * @see org.ifinalframework.data.spi.Consumer
     */
    Object detail(@NonNull K id, @NonNull U user);

    /**
     * @param query the query of count.
     * @param user  the current user.
     * @return the count of query.
     * @see org.ifinalframework.core.IView.Count
     * @see org.ifinalframework.data.spi.QueryConsumer
     */
    Object count(@NonNull @Valid IQuery query, @NonNull U user);

    /**
     * @param query the query of deleted.
     * @param user  the current user.
     * @return deleted rows.
     * @see org.ifinalframework.core.IView.Delete
     * @see org.ifinalframework.data.spi.QueryConsumer
     * @see org.ifinalframework.data.spi.Consumer
     * @see org.ifinalframework.data.spi.BiConsumer
     */
    Object delete(@NonNull @Valid IQuery query, @NonNull U user);

    /**
     * @param id   the id of deleted.
     * @param user the current user.
     * @return deleted rows.
     * @throws org.ifinalframework.context.exception.NotFoundException throw this exception when can not find entity by the {@code id}.
     * @see org.ifinalframework.core.IView.Delete
     * @see org.ifinalframework.data.spi.Consumer
     */
    Object delete(@NonNull K id, @NonNull U user);

    /**
     * @param entity    the entity to update.
     * @param id        the entity id to update.
     * @param selective update selective.
     * @param user      the current user.
     * @return update rows.
     * @see org.ifinalframework.core.IView.Update
     * @see org.ifinalframework.data.spi.Consumer
     * @see org.ifinalframework.data.spi.AfterReturnUpdateConsumer
     */
    Object update(@NonNull T entity, @NonNull K id, boolean selective, @NonNull U user);

    /**
     * @param id   the entity id to update.
     * @param yn   the entity yn to update.
     * @param user the current user.
     * @see BiValidator
     * @see org.ifinalframework.data.spi.BiConsumer
     */
    Object yn(@NonNull K id, @Nullable YN current, @NonNull YN yn, @NonNull U user);

    Object status(@NonNull K id, @NonNull IEnum<?> status, @NonNull U user);

    Object lock(@NonNull K id, @Nullable Boolean current, @NonNull Boolean locked, @NonNull U user);

    Object audit(@NonNull K id, @NonNull AuditValue auditValue, @NonNull U user);

    Object sort(List<SortValue<K>> sort, U user);
}

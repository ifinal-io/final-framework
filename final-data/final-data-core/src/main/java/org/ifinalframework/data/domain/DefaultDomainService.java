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

import org.ifinalframework.core.IEntity;
import org.ifinalframework.core.IEnum;
import org.ifinalframework.core.IQuery;
import org.ifinalframework.core.IUser;
import org.ifinalframework.data.annotation.YN;
import org.ifinalframework.data.domain.action.DeleteAction;
import org.ifinalframework.data.domain.action.DomainActions;
import org.ifinalframework.data.domain.action.InsertAction;
import org.ifinalframework.data.domain.action.SelectAction;
import org.ifinalframework.data.domain.action.UpdateAction;
import org.ifinalframework.data.domain.model.AuditValue;
import org.ifinalframework.data.domain.model.SortValue;
import org.ifinalframework.data.spi.SpiAction;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;

/**
 * DefaultDomainService.
 *
 * @author iimik
 * @version 1.4.3
 * @since 1.4.3
 */
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class DefaultDomainService<K extends Serializable, T extends IEntity<K>, U extends IUser<?>> implements DomainService<K, T, U> {

    private final DomainActions domainActions;

    @NonNull
    @Override
    public Class<T> entityClass() {
        return (Class<T>) domainActions.getEntityClass();
    }

    @Nullable
    @Override
    public Class<?> domainEntityClass(Class<?> prefix) {
        return domainActions.getDomainEntityClasses().get(prefix);
    }

    @NonNull
    @Override
    public Class<? extends IQuery> domainQueryClass(Class<?> prefix) {
        return (Class<? extends IQuery>) domainActions.getDomainQueryClasses().get(prefix);
    }

    @Override
    public Object export(IQuery query, U user) {
        final SelectAction selectAction = (SelectAction) domainActions.getDomainActions().get(SpiAction.Type.EXPORT_BY_QUERY.name());
        return selectAction.select(query, user);
    }

    @Override
    public Object create(@NonNull Object entity, @NonNull U user) {
        final InsertAction insertAction = (InsertAction) domainActions.getDomainActions().get(SpiAction.Type.CREATE.name());
        return insertAction.insert(entity, user);
    }

    @Override
    public Object list(@NonNull IQuery query, @NonNull U user) {
        final SelectAction selectAction = (SelectAction) domainActions.getDomainActions().get(SpiAction.Type.LIST_BY_QUERY.name());
        return selectAction.select(query, user);
    }

    @Override
    public Object detail(@NonNull IQuery query, @NonNull U user) {
        final SelectAction selectAction = (SelectAction) domainActions.getDomainActions().get(SpiAction.Type.DETAIL_BY_QUERY.name());
        return selectAction.select(query, user);

    }

    @Override
    public Object detail(@NonNull K id, @NonNull U user) {
        final SelectAction selectAction = (SelectAction) domainActions.getDomainActions().get(SpiAction.Type.DETAIL_BY_ID.name());
        return selectAction.select(id, user);
    }

    @Override
    public Object count(@NonNull IQuery query, @NonNull U user) {
        final SelectAction selectAction = (SelectAction) domainActions.getDomainActions().get(SpiAction.Type.COUNT_BY_QUERY.name());
        return selectAction.select(query, user);
    }

    @Override
    public Object delete(@NonNull IQuery query, @NonNull U user) {
        final DeleteAction deleteAction = (DeleteAction) domainActions.getDomainActions().get(SpiAction.Type.DELETE_BY_QUERY.name());
        return deleteAction.delete(query, user);

    }

    @Override
    public Object delete(@NonNull K id, @NonNull U user) {
        final DeleteAction deleteAction = (DeleteAction) domainActions.getDomainActions().get(SpiAction.Type.DELETE_BY_ID.name());
        return deleteAction.delete(id, user);
    }

    @Override
    public Object update(@NonNull T entity, @NonNull K id, boolean selective, @NonNull U user) {
        entity.setId(id);
        final UpdateAction updateAction = (UpdateAction) domainActions.getDomainActions().get(SpiAction.Type.UPDATE_BY_ID.name());
        return updateAction.update(id, selective, entity, user);
    }

    @Override
    public Object yn(@NonNull K id, @Nullable YN current, @NonNull YN yn, @NonNull U user) {
        UpdateAction updateAction = (UpdateAction) domainActions.getDomainActions().get(String.join("#", SpiAction.Type.UPDATE_BY_ID.name(), "yn"));
        if (Objects.isNull(updateAction)) {
            updateAction = (UpdateAction) domainActions.getDomainActions().get(String.join("#", SpiAction.Type.UPDATE_BY_ID.name(), "property"));
        }
        return updateAction.update("yn", id, current, yn, user);
    }

    @Override
    public Object status(@NonNull K id, @NonNull IEnum<?> status, @NonNull U user) {
        UpdateAction updateAction = (UpdateAction) domainActions.getDomainActions().get(String.join("#", SpiAction.Type.UPDATE_BY_ID.name(), "status"));
        if (Objects.isNull(updateAction)) {
            updateAction = (UpdateAction) domainActions.getDomainActions().get(String.join("#", SpiAction.Type.UPDATE_BY_ID.name(), "property"));
        }
        return updateAction.update("status", id, null, status, user);
    }

    @Override
    public Object lock(@NonNull K id, @Nullable Boolean current, @NonNull Boolean locked, @NonNull U user) {
        UpdateAction updateAction = (UpdateAction) domainActions.getDomainActions().get(String.join("#", SpiAction.Type.UPDATE_BY_ID.name(), "locked"));
        if (Objects.isNull(updateAction)) {
            updateAction = (UpdateAction) domainActions.getDomainActions().get(String.join("#", SpiAction.Type.UPDATE_BY_ID.name(), "property"));
        }
        return updateAction.update("locked", id, current, locked, user);
    }

    @Override
    public Object audit(@NonNull K id, @NonNull AuditValue auditValue, @NonNull U user) {
        UpdateAction updateAction = (UpdateAction) domainActions.getDomainActions().get(String.join("#", SpiAction.Type.UPDATE_BY_ID.name(), "audit-status"));
        if (Objects.isNull(updateAction)) {
            updateAction = (UpdateAction) domainActions.getDomainActions().get(String.join("#", SpiAction.Type.UPDATE_BY_ID.name(), "property"));
        }
        return updateAction.update("audit-status",id, null, auditValue, user);
    }

    @Override
    public Object sort(List<SortValue<K>> sort, U user) {
        UpdateAction updateAction = (UpdateAction) domainActions.getDomainActions().get(String.join("#", SpiAction.Type.UPDATE_BY_ID.name(), "sort-value"));
        if (Objects.isNull(updateAction)) {
            updateAction = (UpdateAction) domainActions.getDomainActions().get(String.join("#", SpiAction.Type.UPDATE_BY_ID.name(), "property"));
        }
        return updateAction.update(null, null, sort, user);
    }
}

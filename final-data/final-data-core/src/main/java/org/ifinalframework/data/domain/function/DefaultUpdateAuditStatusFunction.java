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

import org.ifinalframework.core.IAudit;
import org.ifinalframework.core.IEntity;
import org.ifinalframework.core.IQuery;
import org.ifinalframework.core.IUser;
import org.ifinalframework.data.domain.model.AuditValue;
import org.ifinalframework.data.query.Update;
import org.ifinalframework.data.repository.Repository;
import org.ifinalframework.data.spi.UpdateFunction;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * DefaultUpdateAuditStatusAction.
 *
 * @author iimik
 * @version 1.5.1
 * @since 1.5.1
 */
@RequiredArgsConstructor
public class DefaultUpdateAuditStatusFunction<K extends Serializable, T extends IEntity<K>, P, U extends IUser<?>>
        implements UpdateFunction<T, P, IAudit.AuditStatus, AuditValue, U> {
    private final Repository<K, T> repository;

    @Override
    public Integer update(List<T> entities, String property, P param, IAudit.AuditStatus auditStatus, AuditValue value, U user) {
        Update update = Update.update()
                .set("audit_status", value.getStatus())
                .set("audit_content", value.getContent())
                .set("audit_date_time", LocalDateTime.now())
                .set("auditor_id", user.getId())
                .set("auditor_name", user.getName());

        if (param instanceof IQuery) {
            return repository.update(update, (IQuery) param);
        } else {
            return repository.update(update, (K) param);
        }

    }

    @Override
    public String getProperty() {
        return "audit-status";
    }
}

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

package org.ifinalframework.data.spi;

import org.ifinalframework.context.exception.BadRequestException;
import org.ifinalframework.core.IAudit;
import org.ifinalframework.core.IRecord;
import org.ifinalframework.core.IUser;
import org.ifinalframework.data.domain.model.AuditValue;

import java.util.Objects;

/**
 * DefaultUpdateAuditStatusPreValidator
 *
 * @author iimik
 * @since 1.5.2
 **/
public class DefaultUpdateAuditStatusPreValidator<U extends IUser<?>> implements BiValidator.ForEach<IAudit<U>, AuditValue, U> {
    @Override
    public void validate(IAudit<U> entity, AuditValue value, U user) {
        final IAudit.AuditStatus auditStatus = entity.getAuditStatus();
        if (Objects.isNull(auditStatus)) {
            return;
        }

        if (value.getStatus() == IAudit.AuditStatus.CANCELED && entity instanceof IRecord<?, ?> record) {
            final IUser<?> creator = record.getCreator();
            if (!Objects.equals(creator.getId(), user.getId())) {
                throw new BadRequestException("仅申请人可以撤销");
            }
        }

        switch (entity.getAuditStatus()) {
            case NONE:
            case PENDING:
                break;
            case CANCELED:
            case AUDITED:
            case REJECTED:
                throw new BadRequestException("当前审核状态已是终态，请勿重复操作");
            default:
                throw new BadRequestException("不支持的操作");
        }

    }
}

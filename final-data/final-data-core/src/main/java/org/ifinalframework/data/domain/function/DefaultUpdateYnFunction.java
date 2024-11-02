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

import org.ifinalframework.context.exception.BadRequestException;
import org.ifinalframework.core.IEntity;
import org.ifinalframework.core.IUser;
import org.ifinalframework.data.annotation.YN;
import org.ifinalframework.data.query.CriterionTarget;
import org.ifinalframework.data.query.PageQuery;
import org.ifinalframework.data.query.Update;
import org.ifinalframework.data.repository.Repository;
import org.ifinalframework.data.spi.UpdateFunction;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * DefaultUpdateLockedAction.
 *
 * @author iimik
 * @version 1.5.1
 * @since 1.5.1
 */
@RequiredArgsConstructor
public class DefaultUpdateYnFunction<K extends Serializable, T extends IEntity<K>, P, U extends IUser<?>>
        implements UpdateFunction<T, P, YN, YN, U> {
    private final Repository<K, T> repository;

    @Override
    public Integer update(List<T> entities, String property, P param, YN current, YN value, U user) {
        Update update = Update.update().set("yn", value);


        if (param instanceof PageQuery pageQuery) {
            pageQuery.where(CriterionTarget.from("yn").eq(current));
            return repository.update(update, pageQuery);
        } else if (param instanceof Collection<?> ids) {
            final PageQuery pageQuery = new PageQuery();
            pageQuery.where(CriterionTarget.from("yn").eq(current),
                    CriterionTarget.from("id").in(ids));
            return repository.update(update, pageQuery);
        } else {
            final PageQuery pageQuery = new PageQuery();
            pageQuery.where(CriterionTarget.from("yn").eq(current),
                    CriterionTarget.from("id").eq(param));

            final int updated = repository.update(update, pageQuery);
            if (updated != 1) {
                throw new BadRequestException("记录已发生变化，请重试");
            }

            return updated;

        }

    }

    @Override
    public String getProperty() {
        return "yn";
    }
}

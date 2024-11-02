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

import java.util.List;

/**
 * UpdateAction.
 *
 * @author iimik
 * @version 1.5.1
 * @see SelectFunction
 * @see DeleteFunction
 * @since 1.5.1
 */
@FunctionalInterface
public interface UpdateFunction<T, P1, P2, V, U> extends UpdateProperty<T> {
    /**
     * Update or delete something with param and value by user.
     *
     * @param entities will update and delete entities.
     * @param param    update or delete param, maybe null.
     * @param value    update value.
     * @param user     operator user.
     * @return update or delete rows.
     */
    default Integer update(List<T> entities, P1 param, P2 param2, V value, U user) {
        return update(entities, null, param, param2, value, user);
    }

    /**
     * Update entity property with param and value by user.
     *
     * @param entities the entity will be updated.
     * @param property the property will be updated, {@code null} means update entity.
     * @param param    the first param.
     * @param param2   the second param.
     * @param value    the value will be updated.
     * @param user     the operator user.
     * @return update rows.
     * @since 1.5.6
     */
    Integer update(List<T> entities, String property, P1 param, P2 param2, V value, U user);

    /**
     * Get update property, {@code null} means update entity.
     *
     * @return update property.
     */
    @Override
    default String getProperty() {
        return null;
    }
}

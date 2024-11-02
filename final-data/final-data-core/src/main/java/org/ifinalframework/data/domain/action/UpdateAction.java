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

package org.ifinalframework.data.domain.action;

/**
 * UpdateAction
 *
 * @author iimik
 * @see org.ifinalframework.data.spi.UpdateFunction
 * @since 1.5.2
 **/
public interface UpdateAction<P1, P2, V, U, R> extends DomainAction {
    /**
     * return updated rows.
     *
     * @param param1 update condition
     * @param param2 update param
     * @param value  update value
     * @param user   update user
     * @return update rows.
     */
    default R update(P1 param1, P2 param2, V value, U user) {
        return update(null, param1, param2, value, user);
    }

    /**
     * return updated rows.
     *
     * @param property the property will be updated, {@code null} means update entity.
     * @param param1   the first param.
     * @param param2   the second param.
     * @param value    the value will be updated.
     * @param user     the operator user.
     * @return update rows.
     * @since 1.5.6
     */
    R update(String property, P1 param1, P2 param2, V value, U user);
}

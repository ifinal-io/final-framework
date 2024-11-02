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
 * @see UpdateFunction
 * @since 1.5.1
 */
@FunctionalInterface
public interface DeleteFunction<T, P, U> {
    /**
     * delete something with param by user.
     *
     * @param entities will update and delete entities.
     * @param param    update or delete param, maybe null.
     * @param user     operator user.
     * @return update or delete rows.
     */
    Integer delete(List<T> entities, P param, U user);
}

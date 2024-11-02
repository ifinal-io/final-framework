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

/**
 * BiDomainActionDispatcher.
 *
 * @author iimik
 * @version 1.5.2
 * @see DomainActionDispatcher
 * @since 1.5.2
 */
@FunctionalInterface
public interface BiDomainActionDispatcher<P1, P2, V, U> {

    /**
     * @param param1 action param.
     * @param param2 action param.
     * @param value  action value.
     * @param user   action user.
     * @deprecated use {@link #dispatch(String, Object, Object, Object, Object)} instead.
     * @see #dispatch(String, Object, Object, Object, Object)
     */
    @Deprecated
    default Object dispatch(P1 param1, P2 param2, V value, U user){
        return dispatch(null, param1, param2, value, user);
    }

    Object dispatch(String property, P1 param1, P2 param2, V value, U user);

}

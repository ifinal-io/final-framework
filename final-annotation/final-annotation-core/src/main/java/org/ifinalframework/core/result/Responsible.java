/*
 * Copyright 2020-2021 the original author or authors.
 *
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
 *
 */

package org.ifinalframework.core.result;

import org.springframework.lang.NonNull;

import org.ifinalframework.core.ResponseStatus;

/**
 * The interface of the response which should be have the response {@linkplain #getStatus() status} and {@linkplain #getDescription()
 * description}.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Responsible {

    /**
     * return the code of this response
     *
     * @return the response code
     * @see ResponseStatus#getCode()
     */
    @NonNull
    Integer getStatus();

    /**
     * return the description of this response.
     *
     * @return the response description.
     * @see ResponseStatus#getDesc()
     */
    @NonNull
    String getDescription();

}

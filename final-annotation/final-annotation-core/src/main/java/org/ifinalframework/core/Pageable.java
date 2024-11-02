/*
 * Copyright 2020-2021 the original author or authors.
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

package org.ifinalframework.core;

import org.springframework.lang.Nullable;

/**
 * The interface for query with {@link Pageable}, do page query only when the {@linkplain #getPage() page} and
 * {@linkplain #getSize()} are not null, and do count query only then the {@linkplain #getCount() count} is {@code
 * true}.
 *
 * @author iimik
 * @version 1.0.0
 * @see Limitable
 * @since 1.0.0
 */
public interface Pageable extends IQuery {

    /**
     * return the page number.
     *
     * @return the page number.
     */
    @Nullable
    Integer getPage();

    void setPage(@Nullable Integer page);

    /**
     * return the page size.
     *
     * @return the page size.
     */
    @Nullable
    Integer getSize();

    void setSize(@Nullable Integer size);

    /**
     * return {@code true} when need to do count, otherwise {@code false}, default need count.
     *
     * @return {@code true} when need to do count, otherwise {@code false}.
     */
    @Nullable
    Boolean getCount();

    void setCount(@Nullable Boolean count);

}

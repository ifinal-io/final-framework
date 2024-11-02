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

package org.ifinalframework.core;

import org.springframework.lang.NonNull;

/**
 * The interface which a service {@link Exception} should be impl. The exception {@linkplain #getCode()} and {@linkplain
 * #getMessage()} should be injected into {@linkplain IResult result}.
 *
 * @author iimik
 * @version 1.0.0
 * @see IResult
 * @see Exception
 * @since 1.0.0
 */
public interface IException {

    /**
     * return the exception code.
     *
     * @return the exception code.
     * @see IResult#getCode()
     */
    @NonNull
    String getCode();

    /**
     * return the exception message for code.
     *
     * @return the exception message for code.
     * @see IResult#getMessage()
     * @see Exception#getMessage()
     */
    @NonNull
    String getMessage();

}

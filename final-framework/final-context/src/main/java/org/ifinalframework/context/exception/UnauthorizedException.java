/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.context.exception;

import org.ifinalframework.core.IException;
import org.ifinalframework.core.ResponseStatus;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class UnauthorizedException extends ServiceException {

    public static final UnauthorizedException DEFAULT = new UnauthorizedException(
            ResponseStatus.UNAUTHORIZED.getDesc());

    public UnauthorizedException(final String message, final Object... args) {

        this(ResponseStatus.UNAUTHORIZED.getCode(), message, args);
    }

    public UnauthorizedException(final IException e, final Object... args) {

        this(e.getCode(), e.getMessage(), args);
    }

    public UnauthorizedException(final Integer code, final String message, final Object... args) {
        this(code.toString(), message, args);
    }

    public UnauthorizedException(final String code, final String message, final Object... args) {
        super(ResponseStatus.UNAUTHORIZED, code, message, args);
    }

}

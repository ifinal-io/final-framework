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
 */

package org.finalframework.context.exception;

import org.finalframework.core.IException;
import org.finalframework.core.ResponseStatus;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class ForbiddenException extends ServiceException {

    public static final ForbiddenException DEFAULT = new ForbiddenException(ResponseStatus.FORBIDDEN.getDesc());

    public ForbiddenException(final String message, final Object... args) {

        this(ResponseStatus.FORBIDDEN.getCode(), message, args);
    }

    public ForbiddenException(final IException e, final Object... args) {

        this(e.getCode(), e.getMessage(), args);
    }

    public ForbiddenException(final Integer code, final String message, final Object... args) {

        this(code.toString(), message, args);
    }

    public ForbiddenException(final String code, final String message, final Object... args) {

        super(ResponseStatus.FORBIDDEN.getCode(), ResponseStatus.FORBIDDEN.getDesc(), code, message, args);
    }

}

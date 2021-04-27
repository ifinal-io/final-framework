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

package org.ifinalframework.context.exception;

import org.ifinalframework.core.IException;
import org.ifinalframework.core.ResponseStatus;

/**
 * 未找到异常，一般为要访问的数据或页面不存在。
 *
 * @author likly
 * @version 1.0.0
 * @see ResponseStatus#BAD_REQUEST
 * @since 1.0.0
 */
public class NotFoundException extends ServiceException {

    public static final NotFoundException DEFAULT = new NotFoundException(ResponseStatus.NOT_FOUND.getDesc());

    public NotFoundException(final String message, final Object... args) {

        this(ResponseStatus.NOT_FOUND.getCode(), message, args);
    }

    public NotFoundException(final IException e, final Object... args) {

        this(e.getCode(), e.getMessage(), args);
    }

    public NotFoundException(final Integer code, final String message, final Object... args) {

        this(code.toString(), message, args);
    }

    public NotFoundException(final String code, final String message, final Object... args) {

        super(ResponseStatus.NOT_FOUND.getCode(), ResponseStatus.NOT_FOUND.getDesc(), code, message, args);
    }

}

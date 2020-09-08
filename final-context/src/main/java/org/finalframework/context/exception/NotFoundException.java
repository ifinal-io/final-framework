/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.context.exception;

import org.finalframework.data.annotation.IException;
import org.finalframework.data.annotation.result.ResponseStatus;

/**
 * 未找到异常，一般为要访问的数据或页面不存在。
 *
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:57:04
 * @see ResponseStatus#BAD_REQUEST
 * @since 1.0
 */
public class NotFoundException extends ServiceException {
    public static final NotFoundException DEFAULT = new NotFoundException(ResponseStatus.NOT_FOUND.getMessage());

    public NotFoundException(String message, Object... args) {
        this(ResponseStatus.NOT_FOUND.getCode(), message, args);
    }

    public NotFoundException(IException e, Object... args) {
        this(e.getCode(), e.getMessage(), args);
    }

    public NotFoundException(Integer code, String message, Object... args) {
        this(code.toString(), message, args);
    }

    public NotFoundException(String code, String message, Object... args) {
        super(ResponseStatus.NOT_FOUND.getCode(), ResponseStatus.NOT_FOUND.getMessage(), code, message, args);
    }


}

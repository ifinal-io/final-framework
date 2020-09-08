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
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:57:04
 * @since 1.0
 */
public class ForbiddenException extends ServiceException {

    public static final ForbiddenException DEFAULT = new ForbiddenException(ResponseStatus.FORBIDDEN.getDesc());


    public ForbiddenException(String message, Object... args) {
        this(ResponseStatus.FORBIDDEN.getCode(), message, args);
    }

    public ForbiddenException(IException e, Object... args) {
        this(e.getCode(), e.getMessage(), args);
    }

    public ForbiddenException(Integer code, String message, Object... args) {
        this(code.toString(), message, args);
    }

    public ForbiddenException(String code, String message, Object... args) {
        super(ResponseStatus.FORBIDDEN.getCode(), ResponseStatus.FORBIDDEN.getDesc(), code, message, args);
    }


}

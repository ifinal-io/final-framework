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

package org.finalframework.annotation.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.finalframework.annotation.IEnum;
import org.finalframework.annotation.data.Transient;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-15 20:50:00
 * @since 1.0
 */
@Transient
@Getter
@AllArgsConstructor
public enum ResponseStatus implements IEnum<Integer> {
    SUCCESS(0, "Success"),
    BAD_REQUEST(400, "Bad Request"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    ;
    private final Integer code;
    private final String desc;

}



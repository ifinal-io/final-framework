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

package org.ifinalframework.web.servlet.response;

import org.ifinalframework.core.result.Column;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * ResponseHelper.
 *
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class ResponseHelper {

    private static final String RESPONSE_COLUMN = ResponseHelper.class + ".responseColumn";

    private ResponseHelper() {
        throw new IllegalAccessError("There is no ResponseHelper instance for you!");
    }

    public static void setResponseColumn(HttpServletRequest request, List<Column> columns) {
        request.setAttribute(RESPONSE_COLUMN, columns);
    }

    @SuppressWarnings("unchecked")
    public static List<Column> getResponseColumn(HttpServletRequest request) {
        Object value = request.getAttribute(RESPONSE_COLUMN);
        if (Objects.isNull(value)) {
            return Collections.emptyList();
        }
        return (List<Column>) value;
    }

}

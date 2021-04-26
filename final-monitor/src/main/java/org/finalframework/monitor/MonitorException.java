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

package org.finalframework.monitor;

import org.finalframework.context.exception.ServiceException;
import org.finalframework.core.IException;

/**
 * Action异常
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public class MonitorException extends ServiceException {

    public MonitorException(final Integer status, final String description, final IException exception,
        final Object... args) {

        super(status, description, exception, args);
    }

    public MonitorException(final Integer status, final String description) {

        super(status, description);
    }

    public MonitorException(final Integer status, final String description, final String code, final String message,
        final Object... args) {

        super(status, description, code, message, args);
    }

}

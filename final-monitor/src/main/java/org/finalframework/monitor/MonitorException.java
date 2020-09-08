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

package org.finalframework.monitor;


import org.finalframework.data.IException;

/**
 * Action异常
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-29 22:34:46
 * @since 1.0
 */
public class MonitorException {
    private final String code;
    private final String message;

    public MonitorException(Integer code, String message) {
        this(code.toString(), message);
    }

    public MonitorException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public MonitorException(IException exception) {
        this(exception.getCode(), exception.getMessage());
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

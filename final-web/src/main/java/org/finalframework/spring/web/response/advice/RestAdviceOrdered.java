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

package org.finalframework.spring.web.response.advice;

import org.springframework.core.Ordered;

/**
 * @author likly
 * @version 1.0
 * @date 2019-09-27 09:52:22
 * @since 1.0
 */
public interface RestAdviceOrdered {
    /**
     * @see Integer#MIN_VALUE
     */
    int HIGHEST_PRECEDENCE = Ordered.HIGHEST_PRECEDENCE;
    int DEFAULT_PRECEDENCE = 0;
    int JSON_VIEW_PRECEDENCE = 800;
    int RESULT_PRECEDENCE = 1000;
    /**
     * @see Integer#MAX_VALUE
     */
    int LOWEST_PRECEDENCE = Ordered.LOWEST_PRECEDENCE;

}

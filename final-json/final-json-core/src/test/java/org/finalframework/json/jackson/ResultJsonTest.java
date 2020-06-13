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

package org.finalframework.json.jackson;

import org.finalframework.data.result.Result;
import org.finalframework.data.user.UserContextHolder;
import org.finalframework.json.Json;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author likly
 * @version 1.0
 * @date 2020-04-16 23:05:23
 * @since 1.0
 */
public class ResultJsonTest {

    private static final Logger logger = LoggerFactory.getLogger(ResultJsonTest.class);


    @Test
    public void testResultJson() {
        Result<Object> result = new Result<>();
        result.setLocale(LocaleContextHolder.getLocale());
        result.setTimeZone(LocaleContextHolder.getTimeZone());
        result.setOperator(UserContextHolder.getUser());

        logger.info(Json.toJson(result));
    }

}

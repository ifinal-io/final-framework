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

package org.ifinalframework.context.result.consumer;

import org.ifinalframework.context.result.ResultConsumer;
import org.ifinalframework.context.user.UserContextHolder;
import org.ifinalframework.core.result.Result;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * CommonResultConsumer.
 *
 * @author ilikly
 * @version 1.2.4
 * @see LocaleContextHolder#getLocale()
 * @see LocaleContextHolder#getTimeZone()
 * @see UserContextHolder#getUser()
 * @since 1.2.4
 */
@Component
public class CommonResultConsumer implements ResultConsumer<Object> {
    @Override
    public void accept(@NonNull Result<Object> result) {
        // set locale
        result.setLocale(LocaleContextHolder.getLocale());
        // set timeZone
        result.setTimeZone(LocaleContextHolder.getTimeZone());
        // set operator
        result.setOperator(UserContextHolder.getUser());
    }

    @Override
    public boolean test(@NonNull Result<?> result) {
        return true;
    }
}

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

package org.ifinalframework.context.exception.result;

import org.ifinalframework.core.result.R;
import org.ifinalframework.core.result.Result;
import org.ifinalframework.util.Asserts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
@Order
@Component
public class UnCatchResultExceptionHandler implements ResultExceptionHandler<Throwable> {

    private static final Logger logger = LoggerFactory.getLogger(UnCatchResultExceptionHandler.class);

    @Override
    public boolean supports(@NonNull Throwable t) {
        return true;
    }

    @NonNull
    @Override
    public Result<?> handle(@NonNull Throwable throwable) {

        logger.error("UnCatchException:", throwable);
        return R.failure(500, Asserts.isEmpty(throwable.getMessage()) ? "UnCatchException" : throwable.getMessage());
    }

}

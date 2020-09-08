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

package org.finalframework.context.exception.result;

import org.finalframework.annotation.IException;
import org.finalframework.annotation.result.R;
import org.finalframework.annotation.result.Result;
import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.finalframework.context.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 11:40
 * @since 1.0
 */
@Order(0)
@SpringComponent
public class IExceptionResultExceptionHandler implements ResultExceptionHandler<IException> {

    private static final Logger logger = LoggerFactory.getLogger(IExceptionResultExceptionHandler.class);


    @Override
    public boolean supports(Throwable throwable) {
        return throwable instanceof IException;
    }


    public Result<?> handle(ServiceException e) {
        return R.failure(e.getStatus(), e.getDescription(), e.getCode(), e.getMessage());
    }

    @Override
    public Result<?> handle(IException e) {
        if (e instanceof ServiceException) {
            return handle((ServiceException) e);
        } else {
            return R.failure(500, e.getMessage(), e.getCode(), e.getMessage());
        }
    }
}

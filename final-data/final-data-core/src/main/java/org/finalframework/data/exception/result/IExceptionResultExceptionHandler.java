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

package org.finalframework.data.exception.result;

import org.finalframework.data.exception.IException;
import org.finalframework.data.exception.ServiceException;
import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-31 11:40
 * @since 1.0
 */
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

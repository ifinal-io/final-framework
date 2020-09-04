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


import org.finalframework.data.annotation.result.R;
import org.finalframework.data.annotation.result.Result;
import org.finalframework.auto.spring.factory.annotation.SpringComponent;
import org.springframework.core.annotation.Order;

import java.sql.SQLException;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-08 16:51:11
 * @see java.sql.SQLIntegrityConstraintViolationException
 * @since 1.0
 */
@Order(0)
@SpringComponent
public class SQLResultExceptionHandler implements ResultExceptionHandler<SQLException> {
    @Override
    public boolean supports(Throwable throwable) {
        return throwable instanceof SQLException;
    }

    @Override
    public Result<?> handle(SQLException throwable) {
        return R.failure(500, throwable.getMessage(), throwable.getErrorCode() + "", throwable.getSQLState());
    }
}


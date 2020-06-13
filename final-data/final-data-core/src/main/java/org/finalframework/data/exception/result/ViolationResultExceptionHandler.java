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

import org.finalframework.data.response.ResponseStatus;
import org.finalframework.data.result.R;
import org.finalframework.data.result.Result;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * The handler is to handle the exception throw by framework of {@link javax.validation.Validator}
 *
 * <ul>
 *     <li>{@link javax.validation.constraints.AssertFalse}</li>
 *     <li>{@link javax.validation.constraints.AssertTrue}</li>
 *     <li>{@link javax.validation.constraints.DecimalMax}</li>
 *     <li>{@link javax.validation.constraints.DecimalMin}</li>
 *     <li>{@link javax.validation.constraints.Digits}</li>
 *     <li>{@link javax.validation.constraints.Email}</li>
 *     <li>{@link javax.validation.constraints.Future}</li>
 *     <li>{@link javax.validation.constraints.FutureOrPresent}</li>
 *     <li>{@link javax.validation.constraints.Max}</li>
 *     <li>{@link javax.validation.constraints.Min}</li>
 *     <li>{@link javax.validation.constraints.Negative}</li>
 *     <li>{@link javax.validation.constraints.NegativeOrZero}</li>
 *     <li>{@link javax.validation.constraints.NotBlank}</li>
 *     <li>{@link javax.validation.constraints.NotEmpty}</li>
 *     <li>{@link javax.validation.constraints.NotNull}</li>
 *     <li>{@link javax.validation.constraints.Null}</li>
 *     <li>{@link javax.validation.constraints.Past}</li>
 *     <li>{@link javax.validation.constraints.PastOrPresent}</li>
 *     <li>{@link javax.validation.constraints.Pattern}</li>
 *     <li>{@link javax.validation.constraints.Positive}</li>
 *     <li>{@link javax.validation.constraints.PositiveOrZero}</li>
 *     <li>{@link javax.validation.constraints.Size}</li>
 * </ul>
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-31 13:25
 * @see ConstraintViolationException
 * @see javax.validation.Valid
 * @since 1.0
 */
@SpringComponent
@ConditionalOnClass(ConstraintViolationException.class)
public class ViolationResultExceptionHandler implements ResultExceptionHandler<ConstraintViolationException> {
    @Override
    public boolean supports(Throwable t) {
        return t instanceof ConstraintViolationException;
    }

    @Override
    public Result<?> handle(ConstraintViolationException e) {
        return R.failure(
                ResponseStatus.BAD_REQUEST.getCode(), e.getConstraintViolations()
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(","))
        );
    }
}

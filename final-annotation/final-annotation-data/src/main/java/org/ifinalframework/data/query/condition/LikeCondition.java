/*
 * Copyright 2020-2023 the original author or authors.
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

package org.ifinalframework.data.query.condition;

import org.springframework.lang.Nullable;

import org.ifinalframework.data.query.Criterion;
import org.ifinalframework.data.query.CriterionExpression;
import org.ifinalframework.data.annotation.criterion.Contains;
import org.ifinalframework.data.annotation.criterion.EndsWith;
import org.ifinalframework.data.annotation.criterion.Like;
import org.ifinalframework.data.annotation.criterion.NotContains;
import org.ifinalframework.data.annotation.criterion.NotEndsWith;
import org.ifinalframework.data.annotation.criterion.NotLike;
import org.ifinalframework.data.annotation.criterion.NotStartsWith;
import org.ifinalframework.data.annotation.criterion.StartsWith;

/**
 * LikeCondition.
 *
 * @author iimik
 * @version 1.0.0
 * @see Like
 * @see NotLike
 * @see StartsWith
 * @see NotStartsWith
 * @see EndsWith
 * @see NotEndsWith
 * @see Contains
 * @see NotContains
 * @since 1.0.0
 */
public interface LikeCondition extends Condition {

    /**
     * @param value value
     * @return criterion
     * @see StartsWith
     * @since 1.2.1
     */
    default Criterion startsWith(@Nullable String value) {
        return condition(CriterionExpression.STARTS_WITH, value);
    }

    /**
     * @param value value
     * @return criterion
     * @see NotStartsWith
     * @since 1.2.1
     */
    default Criterion notStartsWith(@Nullable String value) {
        return condition(CriterionExpression.NOT_STARTS_WITH, value);
    }

    /**
     * @param value value
     * @return criterion
     * @see EndsWith
     * @since 1.2.1
     */
    default Criterion endsWith(@Nullable String value) {
        return condition(CriterionExpression.ENDS_WITH, value);
    }

    /**
     * @param value value
     * @return criterion
     * @see NotEndsWith
     * @since 1.2.1
     */
    default Criterion notEndsWith(@Nullable String value) {
        return condition(CriterionExpression.NOT_ENDS_WITH, value);
    }


    /**
     * @param value value
     * @return criterion
     */
    default Criterion contains(@Nullable String value) {
        return condition(CriterionExpression.CONTAINS, value);
    }

    /**
     * @param value value
     * @return criterion
     */
    default Criterion notContains(@Nullable String value) {
        return condition(CriterionExpression.NOT_CONTAINS, value);
    }

    /**
     * @param value value
     * @return criterion
     */
    default Criterion like(@Nullable String value) {
        return condition(CriterionExpression.LIKE, value);
    }

    /**
     * @param value value
     * @return criterion
     */
    default Criterion notLike(@Nullable String value) {
        return condition(CriterionExpression.NOT_LIKE, value);
    }

}

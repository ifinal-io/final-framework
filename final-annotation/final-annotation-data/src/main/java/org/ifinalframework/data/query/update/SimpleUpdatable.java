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

package org.ifinalframework.data.query.update;

import org.ifinalframework.data.query.CriterionExpression;
import org.ifinalframework.data.query.QProperty;
import org.ifinalframework.data.query.Update;

/**
 * SimpleUpdatable.
 *
 * @author iimik
 * @version 1.3.5
 * @since 1.3.5
 */
public interface SimpleUpdatable extends Updatable {

    default Update set(QProperty<?> property, Object value) {
        return set(property.getColumn(), value);
    }

    /**
     * Update {@code column} with {@code value} use sql like {@code column = #{value}}.
     *
     * @param column the column to update
     * @param value  the update value
     * @return update
     */
    default Update set(String column, Object value) {
        return update(CriterionExpression.UPDATE_SET, column, value);
    }

    default Update inc(QProperty<?> property) {
        return inc(property.getColumn());
    }

    default Update inc(String column) {
        return incr(column, 1);
    }

    default Update incr(QProperty<?> property, Number value) {
        return incr(property.getColumn(), value);
    }

    /**
     * Update {@code column} with {@code value} user sql like {@code column = column + #{value}}.
     *
     * @param column the column to update.
     * @param value  the incr value.
     * @return update.
     */
    default Update incr(String column, Number value) {
        return update(CriterionExpression.UPDATE_INCR, column, value);
    }

    default Update dec(QProperty<?> property) {
        return dec(property.getColumn());
    }

    default Update dec(String column) {
        return decr(column, 1);
    }

    default Update decr(QProperty<?> property, Number value) {
        return decr(property.getColumn(), value);
    }

    default Update decr(String column, Number value) {
        return update(CriterionExpression.UPDATE_DECR, column, value);
    }
}

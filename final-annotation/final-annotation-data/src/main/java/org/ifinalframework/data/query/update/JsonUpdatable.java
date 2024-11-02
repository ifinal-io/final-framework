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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.ifinalframework.data.query.CriterionExpression;
import org.ifinalframework.data.query.QProperty;
import org.ifinalframework.data.query.Update;

/**
 * JsonUpdatable.
 *
 * @author iimik
 * @version 1.3.5
 * @since 1.3.5
 */
public interface JsonUpdatable extends Updatable {
    //    JSON_INSERT
    default Update jsonInsert(QProperty<?> property, String path, Object value) {
        return jsonInsert(property.getColumn(), path, value);
    }

    default Update jsonInsert(QProperty<?> property, Map<String, Object> values) {
        return jsonInsert(property.getColumn(), values);
    }

    default Update jsonInsert(String column, String path, Object value) {
        return jsonInsert(column, Collections.singletonMap(path, value));
    }

    /**
     * {@code column = JSON_INSERT(column,path,value[,path1,value1...])}
     *
     * @param column update column name.
     * @param values json insert path and values.
     * @return update.
     * @since 1.2.1
     */
    default Update jsonInsert(String column, Map<String, Object> values) {
        return update(CriterionExpression.JSON_INSERT, column, values);
    }

    //    JSON_REPLACE
    default Update jsonReplace(QProperty<?> property, String path, Object value) {
        return jsonReplace(property.getColumn(), path, value);
    }

    default Update jsonReplace(QProperty<?> property, Map<String, Object> values) {
        return jsonReplace(property.getColumn(), values);
    }

    default Update jsonReplace(String column, String path, Object value) {
        return jsonReplace(column, Collections.singletonMap(path, value));
    }

    default Update jsonReplace(String column, Map<String, Object> values) {
        return update(CriterionExpression.JSON_REPLACE, column, values);
    }

    //    JSON_SET
    default Update jsonSet(QProperty<?> property, String path, Object value) {
        return jsonSet(property.getColumn(), path, value);
    }

    default Update jsonSet(QProperty<?> property, Map<String, Object> values) {
        return jsonSet(property.getColumn(), values);
    }

    default Update jsonSet(String column, String path, Object value) {
        return jsonSet(column, Collections.singletonMap(path, value));
    }

    /**
     * @param column update column.
     * @param values value type only support {@code string、number and boolean}.
     * @return update.
     * @since 1.2.1
     */
    default Update jsonSet(String column, Map<String, Object> values) {
        return update(CriterionExpression.JSON_SET, column, values);
    }

    default Update jsonRemove(QProperty<?> property, String... path) {
        return jsonRemove(property.getColumn(), path);
    }

    default Update jsonRemove(QProperty<?> property, Collection<String> paths) {
        return jsonRemove(property.getColumn(), paths);
    }

    default Update jsonRemove(String column, String... path) {
        return jsonRemove(column, Arrays.asList(path));
    }

    default Update jsonRemove(String column, Collection<String> paths) {
        return update(CriterionExpression.JSON_REMOVE, column, paths);
    }
}



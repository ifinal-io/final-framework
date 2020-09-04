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

package org.finalframework.data.query.criterion;


import org.finalframework.data.query.SqlNode;
import org.finalframework.data.query.operation.JsonOperation;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-30 14:35:49
 * @since 1.0
 */
public interface JsonContainsCriterion extends SingleCriterion<Object>, SqlNode {

    static JsonContainsCriterion contains(Object target, Object value, String path) {
        return new JsonContainsCriterionImpl(target, JsonOperation.JSON_CONTAINS, value, path);
    }

    static JsonContainsCriterion notContains(Object target, Object value, String path) {
        return new JsonContainsCriterionImpl(target, JsonOperation.NOT_JSON_CONTAINS, value, path);
    }

    String getPath();


}


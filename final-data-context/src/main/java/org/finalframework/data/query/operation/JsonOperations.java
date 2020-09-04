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

package org.finalframework.data.query.operation;


import org.finalframework.data.query.criterion.Criterion;
import org.finalframework.data.query.criterion.JsonContainsCriterion;
import org.finalframework.data.query.criterion.function.CriterionFunction;
import org.finalframework.data.query.criterion.function.SimpleCriterionFunction;
import org.finalframework.data.query.criterion.function.SingleCriterionFunction;
import org.springframework.lang.NonNull;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-31 20:29:21
 * @since 1.0
 */
public interface JsonOperations {

    public static Criterion contains(@NonNull Object doc, @NonNull Object value, @NonNull String path) {
        return JsonContainsCriterion.contains(doc, value, path);
    }

    public static Criterion notContains(@NonNull Object doc, @NonNull Object value, @NonNull String path) {
        return JsonContainsCriterion.notContains(doc, value, path);
    }

    public static CriterionFunction extract(@NonNull Object doc, @NonNull String path) {
        return new SingleCriterionFunction(doc, JsonOperation.JSON_EXTRACT, path);
    }

    public static CriterionFunction unquote(@NonNull Object doc) {
        return new SimpleCriterionFunction(doc, JsonOperation.JSON_UNQUOTE);
    }


    public static CriterionFunction keys(@NonNull Object doc) {
        return new SimpleCriterionFunction(doc, JsonOperation.JSON_KEYS);
    }


    public static CriterionFunction depth(@NonNull Object doc) {
        return new SimpleCriterionFunction(doc, JsonOperation.JSON_DEPTH);
    }


    public static CriterionFunction length(@NonNull Object doc) {
        return new SimpleCriterionFunction(doc, JsonOperation.JSON_LENGTH);
    }

    public static CriterionFunction object(@NonNull Object doc) {
        return new SimpleCriterionFunction(doc, JsonOperation.JSON_OBJECT);
    }

    public static CriterionFunction array(@NonNull Object doc) {
        return new SimpleCriterionFunction(doc, JsonOperation.JSON_ARRAY);
    }


}


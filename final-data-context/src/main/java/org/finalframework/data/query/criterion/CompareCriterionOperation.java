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

import org.finalframework.core.Builder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-06 20:34:28
 * @since 1.0
 */
public interface CompareCriterionOperation extends CriterionOperation {

    static CompareCriterionOperationBuilder builder() {
        return AbsCompareCriterionOperation.builder();
    }

    @NonNull
    Object getTarget();

    @Override
    CompareOperation getOperation();

    @Nullable
    Object getValue();

    @Nullable
    Object getMin();

    @Nullable
    Object getMax();


    interface CompareCriterionOperationBuilder extends Builder<CompareCriterionOperation> {

        CompareCriterionOperationBuilder target(Object target);

        CompareCriterionOperationBuilder operation(CompareOperation operation);

        CompareCriterionOperationBuilder value(Object value);

        CompareCriterionOperationBuilder min(Object min);

        CompareCriterionOperationBuilder max(Object max);

    }


}

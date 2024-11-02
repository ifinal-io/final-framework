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

package org.ifinalframework.data.annotation.criterion;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;

import org.ifinalframework.data.query.CriterionAttributes;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public interface CriterionSqlProvider {

    /**
     * function sql
     * @param annotation function
     * @param metadata metadata
     */
    void function(@NonNull AnnotationAttributes annotation, @NonNull CriterionAttributes metadata);

    /**
     * order sql
     * @param order order
     * @param metadata order metadata
     * @return order sql
     */
    String order(@NonNull AnnotationAttributes order, @NonNull CriterionAttributes metadata);

    /**
     * criterion sql
     * @param criterion criterion sql
     * @param metadata criterion metadata
     * @return criterion sql
     */
    String provide(@NonNull AnnotationAttributes criterion, @NonNull CriterionAttributes metadata);

}

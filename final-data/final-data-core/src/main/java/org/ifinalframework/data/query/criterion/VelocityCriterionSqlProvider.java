/*
 * Copyright 2020-2021 the original author or authors.
 *
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

package org.ifinalframework.data.query.criterion;

import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;

import org.ifinalframework.data.annotation.criterion.CriterionSqlProvider;
import org.ifinalframework.data.query.CriterionAttributes;
import org.ifinalframework.velocity.Velocities;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public class VelocityCriterionSqlProvider implements CriterionSqlProvider {

    private static final String ATTRIBUTE_EXPRESSION_KEY = "expression";

    private static final String DEFAULT_DELIMITER = " ";

    @Override
    public void function(@NonNull final AnnotationAttributes function,
                         @NonNull final CriterionAttributes metadata) {

        String value = Arrays.stream(function.getStringArray(ATTRIBUTE_EXPRESSION_KEY))
                .map(String::trim)
                .collect(Collectors.joining(DEFAULT_DELIMITER));

        final String column = Velocities.getValue(value, metadata);

        metadata.put("column", column);

    }

    @Override
    public String order(@NonNull final AnnotationAttributes annotation, @NonNull final CriterionAttributes metadata) {
        String value = Arrays.stream(annotation.getStringArray(ATTRIBUTE_EXPRESSION_KEY))
                .map(String::trim)
                .collect(Collectors.joining(DEFAULT_DELIMITER));
        final String order = Velocities.getValue(value, metadata);
        return order;
    }

    @Override
    public String provide(@NonNull final AnnotationAttributes criterion, @NonNull final CriterionAttributes metadata) {
        final String value = Arrays.stream(criterion.getStringArray(ATTRIBUTE_EXPRESSION_KEY))
                .map(String::trim)
                .collect(Collectors.joining(DEFAULT_DELIMITER));
        return Velocities.getValue(value, metadata);
    }

}


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

package org.finalframework.data.annotation.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The meta annotation for criterion.
 *
 * @author likly
 * @version 1.0
 * @date 2019-02-20 09:25:43
 * @see MeteData
 * @see CriterionHandler
 * @see EQUAL
 * @see NOT_EQUAL
 * @see GREAT_THAN
 * @see GREAT_THAN_EQUAL
 * @see LESS_THAN
 * @see LESS_THAN_EQUAL
 * @see LIKE
 * @see NOT_LIKE
 * @see CONTAINS
 * @see NOT_CONTAINS
 * @see START_WITH
 * @see NOT_START_WITH
 * @see END_WITH
 * @see NOT_END_WITH
 * @see IN
 * @see NOT_IN
 * @see BETWEEN
 * @see NOT_BETWEEN
 * @since 1.0
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Criterion {

    String property() default "";

    String[] value() default {};

    Class<?> javaType() default Object.class;

    Class<? extends CriterionHandler> handler() default CriterionHandler.class;

    Attribute[] attributes() default {};

    @interface Attribute {
        String name();

        String value();
    }

}

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

package org.ifinalframework.data.query;

import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * CriterionTargetTest.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class CriterionTargetTest {

    public static final String COLUMN = "name";

    public static final String VALUE = "haha";

    private static final int MIN = 1;

    private static final int MAX = 10;

    private static final BetweenValue<Integer> BETWEEN_VALUE = new BetweenValue<>(MIN, MAX);

    private static final List<Integer> IN_VALUE = Arrays.asList(MIN, MAX);

}

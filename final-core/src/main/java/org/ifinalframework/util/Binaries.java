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

package org.ifinalframework.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ilikly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Binaries {

    private static final int BIN_STEP = 1;

    private static final Integer BINARY_0 = 1;

    private static final Integer BINARY_1 = BINARY_0 << BIN_STEP;

    private static final Integer BINARY_2 = BINARY_1 << BIN_STEP;

    private static final Integer BINARY_3 = BINARY_2 << BIN_STEP;

    private static final Integer BINARY_4 = BINARY_3 << BIN_STEP;

    private static final Integer BINARY_5 = BINARY_4 << BIN_STEP;

    private static final Integer BINARY_6 = BINARY_5 << BIN_STEP;

    private static final Integer BINARY_7 = BINARY_6 << BIN_STEP;

    private static final Integer BINARY_8 = BINARY_7 << BIN_STEP;

    private static final Integer BINARY_9 = BINARY_8 << BIN_STEP;

    private static final Integer BINARY_10 = BINARY_9 << BIN_STEP;

    private static final Integer BINARY_11 = BINARY_10 << BIN_STEP;

    private static final Integer BINARY_12 = BINARY_11 << BIN_STEP;

    private static final Integer BINARY_13 = BINARY_12 << BIN_STEP;

    private static final Integer BINARY_14 = BINARY_13 << BIN_STEP;

    private static final Integer BINARY_15 = BINARY_14 << BIN_STEP;

    private static final Integer BINARY_16 = BINARY_15 << BIN_STEP;

    private static final Integer BINARY_17 = BINARY_16 << BIN_STEP;

    private static final Integer BINARY_18 = BINARY_17 << BIN_STEP;

    private static final Integer BINARY_19 = BINARY_18 << BIN_STEP;

    private static final Integer BINARY_20 = BINARY_19 << BIN_STEP;

    private static final Integer BINARY_21 = BINARY_20 << BIN_STEP;

    private static final Integer BINARY_22 = BINARY_21 << BIN_STEP;

    private static final Integer BINARY_23 = BINARY_22 << BIN_STEP;

    private static final Integer BINARY_24 = BINARY_23 << BIN_STEP;

    private static final Integer BINARY_25 = BINARY_24 << BIN_STEP;

    private static final Integer BINARY_26 = BINARY_25 << BIN_STEP;

    private static final Integer BINARY_27 = BINARY_26 << BIN_STEP;

    private static final Integer BINARY_28 = BINARY_27 << BIN_STEP;

    private static final Integer BINARY_29 = BINARY_28 << BIN_STEP;

    private static final Integer BINARY_30 = BINARY_29 << BIN_STEP;

    private static final Integer BINARY_31 = BINARY_30 << BIN_STEP;

    private static final Set<Integer> CACHE = new HashSet<>();

    static {
        CACHE.addAll(
                Arrays.asList(
                        BINARY_0, BINARY_1, BINARY_2, BINARY_3, BINARY_4,
                        BINARY_5, BINARY_6, BINARY_7, BINARY_8, BINARY_9,
                        BINARY_10, BINARY_11, BINARY_12, BINARY_13, BINARY_14,
                        BINARY_15, BINARY_16, BINARY_17, BINARY_18, BINARY_19,
                        BINARY_20, BINARY_21, BINARY_22, BINARY_23, BINARY_24,
                        BINARY_25, BINARY_26, BINARY_27, BINARY_28, BINARY_29,
                        BINARY_30, BINARY_31
                )
        );
    }

    private Binaries() {

    }

    public static Collection<Integer> flat(final Integer binary) {

        return CACHE.stream()
                .filter(it -> (it & binary) == it)
                .collect(Collectors.toList());
    }

    public static Integer merge(final Collection<Integer> binaries) {

        return new HashSet<>(binaries).stream().reduce(0, (a, b) -> a | b);
    }

}

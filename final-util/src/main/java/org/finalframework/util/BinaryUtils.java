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

package org.finalframework.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2019-07-15 09:38
 * @since 1.0
 */
public final class BinaryUtils {
    private static final Integer BINARY_0 = 1;
    private static final Integer BINARY_1 = 1 << 1;
    private static final Integer BINARY_2 = 1 << 2;
    private static final Integer BINARY_3 = 1 << 3;
    private static final Integer BINARY_4 = 1 << 4;
    private static final Integer BINARY_5 = 1 << 5;
    private static final Integer BINARY_6 = 1 << 6;
    private static final Integer BINARY_7 = 1 << 7;
    private static final Integer BINARY_8 = 1 << 8;
    private static final Integer BINARY_9 = 1 << 9;
    private static final Integer BINARY_10 = 1 << 10;
    private static final Integer BINARY_11 = 1 << 11;
    private static final Integer BINARY_12 = 1 << 12;
    private static final Integer BINARY_13 = 1 << 13;
    private static final Integer BINARY_14 = 1 << 14;
    private static final Integer BINARY_15 = 1 << 15;
    private static final Integer BINARY_16 = 1 << 16;
    private static final Integer BINARY_17 = 1 << 17;
    private static final Integer BINARY_18 = 1 << 18;
    private static final Integer BINARY_19 = 1 << 19;
    private static final Integer BINARY_20 = 1 << 20;
    private static final Integer BINARY_21 = 1 << 21;
    private static final Integer BINARY_22 = 1 << 22;
    private static final Integer BINARY_23 = 1 << 23;
    private static final Integer BINARY_24 = 1 << 24;
    private static final Integer BINARY_25 = 1 << 25;
    private static final Integer BINARY_26 = 1 << 26;
    private static final Integer BINARY_27 = 1 << 27;
    private static final Integer BINARY_28 = 1 << 28;
    private static final Integer BINARY_29 = 1 << 29;
    private static final Integer BINARY_30 = 1 << 30;
    private static final Integer BINARY_31 = 1 << 31;

    private static final Set<Integer> BINARIES = new HashSet<>();

    static {
        BINARIES.addAll(
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

    public static Collection<Integer> flat(Integer binary) {
        return BINARIES.stream()
                .filter(it -> (it & binary) == it)
                .collect(Collectors.toList());
    }

    public static Integer merge(Collection<Integer> binaries) {
        return new HashSet<>(binaries).stream().reduce(0, Integer::sum);
    }

    public static void main(String[] args) {
        System.out.println(BinaryUtils.merge(Arrays.asList(1, 2, 4, 4)));
        System.out.println("7168=" + BinaryUtils.flat(7168));
    }

}

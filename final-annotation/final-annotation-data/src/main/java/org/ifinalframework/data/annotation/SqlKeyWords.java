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
 *
 */

package org.ifinalframework.data.annotation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.lang.NonNull;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public final class SqlKeyWords {

    private static final Set<String> KEYS = new HashSet<>();

    static {
        init("key", "order", "group", "source", "user","from","to");
    }

    private SqlKeyWords() {
        throw new IllegalAccessError("There is no instance for you!");
    }

    private static void init(final String... keys) {
        Arrays.stream(keys).map(String::toLowerCase).forEach(SqlKeyWords.KEYS::add);
    }

    /**
     * @param word test word
     * @return {@code true} if the test word is a keyword.
     * @since 1.4.2
     */
    public static boolean isKeyWord(String word) {
        return KEYS.contains(word.toLowerCase());
    }

    /**
     * @param word test word
     * @return {@code true} if the test word is a keyword.
     * @see #isKeyWord(String)
     * @deprecated replaced by {@link #isKeyWord(String)}.
     */
    @Deprecated
    public static boolean contains(final @NonNull String word) {
        return isKeyWord(word);
    }

}


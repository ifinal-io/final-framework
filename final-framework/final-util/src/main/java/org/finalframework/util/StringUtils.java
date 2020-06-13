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

import lombok.NonNull;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-15 13:35
 * @since 1.0
 */
@SuppressWarnings("unused")
public interface StringUtils {


    static String join(@NonNull Collection<?> collection, @NonNull String separator) {
        return join(collection, separator, null, null);
    }

    static String join(@NonNull Collection<?> collection, @NonNull String separator, String open, String close) {
        final StringBuilder sb = new StringBuilder();

        if (open != null) {
            sb.append(open);
        }

        int i = 0;
        for (Object item : collection) {
            if (i++ != 0) {
                sb.append(separator);
            }
            sb.append(item.toString());
        }

        if (close != null) {
            sb.append(close);
        }

        return sb.toString();
    }

    static String replaceFirst(@NonNull String text, @NonNull String source, @NonNull String target) {
        return text.replaceFirst(source, target);
    }

    static String replaceLast(@NonNull String text, @NonNull String source, @NonNull String target) {
        final int index = text.lastIndexOf(source);
        if (index != -1) {
            return text.substring(0, index) + target;
        }
        return text;
    }

    static String[] split(@NonNull String text, @NonNull String separator) {
        return split(text, separator, null, null);
    }

    static String[] split(@NonNull String text, @NonNull String separator, String open, String close) {
        if (open != null) {
            text = text.replaceFirst(open, "");
        }
        if (close != null) {
            text = replaceLast(text, close, "");
        }
        return text.split(separator);
    }
}

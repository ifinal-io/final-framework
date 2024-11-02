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

package org.ifinalframework.core;

import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * A maker superinterface which the {@linkplain Enum enum} should be impl.
 *
 * <pre class="code">
 * &#064;Getter
 * &#064;AllArgsConstructor
 * public enum YN implements IEnum&lt;Integer&gt; {
 *      YES(1,"有效"),
 *      NO(0,"无效");
 *      private final Integer code;
 *      private final String desc;
 * }
 *
 * public class MyEntity{
 *     private YN yn;
 * }
 * </pre>
 * get the json when serialize the data of MyEntity.
 * <pre class="code">
 * {
 *     "yn": 1,
 *     "ynName": YES,
 *     "ynDesc": "有效"
 * }
 * </pre>
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IEnum<T> {

    /**
     * return the {@link IEnum} value of {@code code}.
     *
     * @param type the enum type
     * @param code the enum code
     * @param <T>  enum type
     * @return enum value of code
     */
    @Nullable
    static <T extends IEnum<?>> T valueOf(@NonNull Class<T> type, @Nullable Object code) {

        if (Objects.isNull(code)) {
            return null;
        }

        final T[] constants = Objects.requireNonNull(type, "enum type can not be null").getEnumConstants();
        for (T constant : constants) {
            if (Objects.equals(constant.getCode(), code)) {
                return constant;
            }
        }
        return null;
    }

    /**
     * return the enum code, not the {@linkplain Enum#ordinal() ordinal}.
     *
     * @return enum code
     */
    @NonNull
    T getCode();

    /**
     * return the enum desc, not the {@linkplain Enum#name() name}.
     *
     * @return enum desc
     */
    @NonNull
    String getDesc();

}

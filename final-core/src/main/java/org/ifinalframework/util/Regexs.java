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

import java.util.regex.Pattern;

/**
 * 正则匹配工具类.
 *
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Regexs {

    private Regexs() {
    }

    /**
     * 校验给定的字符串是否满足正则校验
     * @since 1.6.0
     * @param regex 正则表达式
     * @param input 要校验的字符串
     * @return 是否满足正则
     */
    public static boolean matches(String regex, CharSequence input){
        return Pattern.matches(regex, input);
    }

    /**
     * @see #matches(String, CharSequence)
     * @param pattern
     * @param input
     * @return
     * @deprecated 1.6.0 
     * 
     */
    @Deprecated
    public static boolean matches(final Pattern pattern, final CharSequence input) {
        return pattern.matcher(input).matches();
    }

}

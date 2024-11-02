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

package org.ifinalframework.util.function;

/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@FunctionalInterface
public interface Converter3<S1, S2, S3, R> {

    /**
     * convert to {@link R} with {@link S1} , {@link S2} and {@link S3}
     */
    R convert(S1 s1, S2 s2, S3 s3);

}

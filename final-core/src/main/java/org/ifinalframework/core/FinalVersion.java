/*
 * Copyright 2020-2022 the original author or authors.
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

package org.ifinalframework.core;

import lombok.experimental.UtilityClass;
import org.ifinalframework.util.JarVersions;

/**
 * FinalVersion.
 *
 * @author ilikly
 * @version 1.2.4
 * @see org.springframework.core.SpringVersion
 * @see org.springframework.boot.SpringBootVersion
 * @since 1.2.4
 */
@UtilityClass
public final class FinalVersion {

    /**
     * return the version of final framework.
     */
    public static String getVersion() {
        return JarVersions.getVersion(FinalVersion.class);
    }
}

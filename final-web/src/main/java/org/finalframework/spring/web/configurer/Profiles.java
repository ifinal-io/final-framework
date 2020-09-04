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

package org.finalframework.spring.web.configurer;

import org.springframework.context.annotation.Profile;

/**
 * 常用环境变量
 *
 * @author likly
 * @version 1.0
 * @date 2019-03-24 15:53:20
 * @see Profile
 * @since 1.0
 */
public final class Profiles {

    public static final String LOCAL = "local";
    public static final String DEV = "dev";
    public static final String TEST = "test";
    public static final String PRODUCT = "product";

    private Profiles() {
        throw new IllegalArgumentException("Profiles 不支持实例化");
    }

}

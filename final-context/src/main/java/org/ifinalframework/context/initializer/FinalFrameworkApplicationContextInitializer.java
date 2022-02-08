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

package org.ifinalframework.context.initializer;

import lombok.extern.slf4j.Slf4j;
import org.ifinalframework.FinalFramework;
import org.ifinalframework.auto.spring.factory.annotation.SpringFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Registration a {@link FinalFramework} class into {@link ConfigurableApplicationContext}.
 *
 * @author likly
 * @version 1.0.0
 * @see FinalFramework
 * @since 1.0.0
 */
@Slf4j
@SpringFactory(ApplicationContextInitializer.class)
public final class FinalFrameworkApplicationContextInitializer extends
    AbsFrameworkApplicationContextInitializer<ConfigurableApplicationContext> {

    public FinalFrameworkApplicationContextInitializer() {
        super(FinalFramework.class);
        logger.info("registered framework: {}", FinalFramework.class.getSimpleName());
    }

}

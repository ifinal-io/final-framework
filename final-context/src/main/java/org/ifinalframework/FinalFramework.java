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

package org.ifinalframework;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.stereotype.Component;

/**
 * An {@link Configuration} class for {@code finalframework}.
 *
 * <h3>@ComponentScan</h3>
 * <p>{@link FinalFramework} use default packages of {@code org.ifinalframework} to scan {@link Component}s.</p>
 *
 * @author likly
 * @version 1.0.0
 * @see ComponentScan
 * @see ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry(BeanDefinitionRegistry)
 * @see org.springframework.context.ApplicationContextInitializer
 * @see org.ifinalframework.context.initializer.FinalFrameworkApplicationContextInitializer
 * @since 1.0.0
 */
@Slf4j
@ComponentScan
public class FinalFramework {
}

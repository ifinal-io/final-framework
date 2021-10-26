/*
 * Copyright 2020-2021 the original author or authors.
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

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

/**
 * An {@link Configuration} class for {@code finalframework}.
 *
 * <h3>@ComponentScan</h3>
 * <p>{@link FinalFramework} use default packages of {@code org.ifinalframework} to scan {@link Component}s.</p>
 *
 * <h3>@ImportResource</h3>
 * <p>Import the resources from:</p>
 * <ul>
 *     <li>{@code classpath:spring-config-*.xml}</li>
 *     <li>{@code classpath*:config/spring-config-*.xml}</li>
 *     <li>{@code classpath*:spring/spring-config-*.xml}</li>
 * </ul>
 *
 * @author likly
 * @version 1.0.0
 * @see ComponentScan
 * @see ImportResource
 * @see ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry(BeanDefinitionRegistry)
 * @see org.springframework.context.ApplicationContextInitializer
 * @see org.ifinalframework.context.initializer.FinalFrameworkApplicationContextInitializer
 * @since 1.0.0
 */
@Slf4j
@ComponentScan
@ImportResource({
        FinalFramework.CLASS_PATH_SPRING_CONFIG_XML,
        FinalFramework.CLASS_PATH_CONFIG_SPRING_CONFIG_XML,
        FinalFramework.CLASS_PATH_SPRING_SPRING_CONFIG_XML
})
public class FinalFramework implements BeanNameAware {

    static final String CLASS_PATH_SPRING_CONFIG_XML = "classpath:spring-config-*.xml";

    static final String CLASS_PATH_CONFIG_SPRING_CONFIG_XML = "classpath*:config/spring-config-*.xml";

    static final String CLASS_PATH_SPRING_SPRING_CONFIG_XML = "classpath*:spring/spring-config-*.xml";

    @Getter
    @Setter
    private String beanName;

}

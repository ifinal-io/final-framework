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

package org.ifinalframework.beans.factory.support;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Load {@link org.springframework.beans.factory.config.BeanDefinition}s from resource like {@link
 * org.springframework.context.annotation.ImportResource}.
 *
 * <p>Config Import Resource Locations</p>
 * <p>Config the locations of import resource with {@code spring.application.import-resource.locations}.</p>
 *
 * <p>Enable Default Import Resource</p>
 * <p>Config use default import resource with {@code spring.application.import-resource.use-default}, default
 * locations is:</p>
 * <ul>
 *     <li>classpath:spring-config-*.xml</li>
 *     <li>classpath*:config/spring-config-*.xml</li>
 *     <li>classpath*:spring/spring-config-*.xml</li>
 * </ul>
 *
 * @author ilikly
 * @version 1.2.4
 * @see org.springframework.context.annotation.ImportResource
 * @see org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsFromImportedResources
 * @since 1.2.4
 */
@Slf4j
@Component
public class ImportResourceBeanDefinitionRegistryPostProcessor extends OnceBeanDefinitionRegistryPostProcessor
        implements EnvironmentAware, ResourceLoaderAware {


    private static final String[] DEFAULT_RESOURCE_LOCATIONS = new String[]{
            "classpath:spring-config-*.xml",
            "classpath*:config/spring-config-*.xml",
            "classpath*:spring/spring-config-*.xml"
    };

    @Setter
    private Environment environment;
    @Setter
    private ResourceLoader resourceLoader;

    @Override
    protected void processBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry registry) throws BeansException {

        final BeanDefinitionReaderFactory beanDefinitionReaderFactory = new BeanDefinitionReaderFactory(environment, resourceLoader, registry);

        final Set<String> importResources = new LinkedHashSet<>();

        // load developer's import resource locations
        final String[] locations = environment.getProperty("spring.application.import-resource.locations", String[].class);

        if (Objects.nonNull(locations) && locations.length > 0) {
            importResources.addAll(Arrays.asList(locations));
        }

        // load default import resource locations
        final Boolean useDefault = environment.getProperty("spring.application.import-resource.use-default", boolean.class, true);
        if (Boolean.TRUE.equals(useDefault)) {
            importResources.addAll(Arrays.asList(DEFAULT_RESOURCE_LOCATIONS));
        }

        logger.info("start load import resources: {}", importResources);

        logger.info("┏╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍");
        logger.info("┃                                      @{}", ImportResource.class.getSimpleName());
        logger.info("┠╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌╌");

        for (String resource : importResources) {
            final BeanDefinitionReader reader = beanDefinitionReaderFactory.create(resource);
            final int count = reader.loadBeanDefinitions(resource);
            logger.info("┃  loaded {} bean definitions from resource of {}.", count, resource);
        }
        logger.info("┗╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍╍");
    }


}

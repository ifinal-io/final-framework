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

package org.ifinalframework.data.mybatis.configuration;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;

import org.ifinalframework.core.IEntity;
import org.ifinalframework.core.lang.Transient;
import org.ifinalframework.data.mybatis.handler.EnumTypeHandler;
import org.ifinalframework.data.mybatis.reflection.FinalObjectWrapperFactory;
import org.ifinalframework.data.mybatis.reflection.factory.ObjectFactoryWrapper;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author iimik
 * @version 1.0.0
 * @see org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration
 * @since 1.0.0
 */
@Slf4j
@Component
@Order(100)
public class FinalMybatisConfigurationCustomizer implements ConfigurationCustomizer, BeanFactoryAware,
        InitializingBean {

    @Setter
    private BeanFactory beanFactory;

    @Setter
    private List<String> packages;

    private final ConfigurationBiConsumer configurationBiConsumer = new ConfigurationBiConsumerComposite();


    @Override
    @SuppressWarnings("unchecked")
    public void customize(final Configuration configuration) {

        // add AbsMapper
        //        configuration.addMapper(AbsMapper.class);

        //        PropertyTokenizerRedefiner.redefine();

        // set default enum type handler
        logger.info("setDefaultEnumTypeHandler:{}", EnumTypeHandler.class.getCanonicalName());
        configuration.getTypeHandlerRegistry().setDefaultEnumTypeHandler(EnumTypeHandler.class);
        configuration.setObjectWrapperFactory(new FinalObjectWrapperFactory());
        configuration.setObjectFactory(new ObjectFactoryWrapper(configuration.getObjectFactory()));
        Properties properties = configuration.getVariables();
        properties.setProperty("mapUnderscoreToCamelCase", "true");

        // scan entity class
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(
                false);

        scanner.addIncludeFilter(new AssignableTypeFilter(IEntity.class));
        scanner.addExcludeFilter(new AnnotationTypeFilter(Transient.class));

        Set<BeanDefinition> entities = new LinkedHashSet<>();
        packages.forEach(it -> entities.addAll(scanner.findCandidateComponents(it)));

        entities.stream()
                .map(BeanDefinition::getBeanClassName)
                .map(className -> {
                    try {
                        return Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        throw new IllegalArgumentException(className);
                    }
                })
                .forEach(clazz -> {
                    configurationBiConsumer.accept(configuration, (Class<? extends IEntity<?>>) clazz);
                });

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.packages = AutoConfigurationPackages.get(this.beanFactory);
    }

}


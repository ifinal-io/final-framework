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

package org.ifinalframework.data.sharding.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import org.ifinalframework.core.IEntity;
import org.ifinalframework.io.support.ServicesLoader;
import org.ifinalframework.sharding.annotation.Property;
import org.ifinalframework.sharding.annotation.ShardingStrategy;
import org.ifinalframework.sharding.annotation.ShardingTable;

import jakarta.annotation.PostConstruct;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


/**
 * @author iimik
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Component
public class IEntityShardingConfigurer implements ShardingConfigurer, BeanFactoryAware {

    private static final String LOGIN_TABLE_PLACE_HOLDER = "${logicTable}";

    private static final Set<Class<? extends Annotation>> SHARDING_STRATEGY_ANNOTATIONS =
            ServicesLoader.loadClasses(ShardingStrategy.class, ShardingStrategy.class.getClassLoader())
                    .stream()
                    .map(clazz -> (Class<? extends Annotation>) clazz)
                    .collect(Collectors.toSet());

    private final Collection<ShardingTableRegistration> tables = new ArrayList<>();

    private final Collection<String> bindTables = new ArrayList<>();

    private final Collection<String> broadcastTables = new ArrayList<>();

    private final Map<String, ShardingStrategyRegistration> shardingStrategies = new LinkedHashMap<>();

    @Setter
    private BeanFactory beanFactory;

    @PostConstruct
    public void init() {

        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(
                false);

        scanner.addIncludeFilter(new AssignableTypeFilter(IEntity.class));

        final Set<BeanDefinition> beanDefinitions = new LinkedHashSet<>();

        AutoConfigurationPackages.get(beanFactory)
                .forEach(it -> beanDefinitions.addAll(scanner.findCandidateComponents(it)));

        beanDefinitions.stream()
                .map(BeanDefinition::getBeanClassName)
                .map(it -> {
                    try {
                        return Class.forName(it);
                    } catch (ClassNotFoundException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .filter(it -> it.isAnnotationPresent(ShardingTable.class))
                .forEach(this::parseShardingTable);

    }

    private void parseShardingTable(final Class<?> clazz) {

        ShardingTable shardingTable = clazz.getAnnotation(ShardingTable.class);
        Collection<Annotation> shardingStrategyAnnotations = findAllShardingStrategyAnnotations(clazz);

        final String actualDataNodes = String.join(",", shardingTable.actualDataNodes());

        for (String logicTable : shardingTable.logicTables()) {

            if (shardingTable.broadcast()) {
                broadcastTables.add(logicTable);
            }

            final ShardingTableRegistration table = new ShardingTableRegistration(logicTable,
                    actualDataNodes.replace(LOGIN_TABLE_PLACE_HOLDER, logicTable));

            for (Annotation annotation : shardingStrategyAnnotations) {

                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType.isAnnotationPresent(ShardingStrategy.class)) {
                    parseShardingStrategy(clazz, logicTable, table, annotation, annotationType);
                }
            }

            if (logger.isInfoEnabled()) {
                logger.info("add sharding table: {}", table);
            }

            tables.add(table);

        }
    }

    private void parseShardingStrategy(final Class<?> clazz, final String logicTable,
                                       final ShardingTableRegistration table,
                                       final Annotation annotation, final Class<? extends Annotation> annotationType) {
        ShardingStrategy shardingStrategy = annotationType.getAnnotation(ShardingStrategy.class);

        AnnotationAttributes annotationAttributes = AnnotationUtils.getAnnotationAttributes(clazz, annotation);

        ShardingStrategy.Scope scope = annotationAttributes.getEnum("scope");
        final String name = buildShardingStrategyName(logicTable, scope, shardingStrategy.type());

        final Properties properties = parseAnnotationToProperties(logicTable, annotationAttributes);

        // process class based sharding strategy properties
        if (ShardingStrategy.Strategy.CLASS_BASED == shardingStrategy.strategy()) {
            processClassBasedSharingStrategyProperties(properties);
        }

        ShardingStrategyRegistration shardingStrategyRegistration
                = buildShardingStrategyConfiguration(shardingStrategy, name, annotationAttributes, properties);
        switch (scope) {
            case TABLE:
                table.setTableShardingStrategy(shardingStrategyRegistration);
                break;
            case DATABASE:
                table.setDatabaseShardingStrategy(shardingStrategyRegistration);
                break;
            default:
                throw new IllegalArgumentException(scope.name());
        }

        shardingStrategies.put(name, shardingStrategyRegistration);
    }

    private Properties parseAnnotationToProperties(final String logicTable,
                                                   final AnnotationAttributes annotationAttributes) {
        final Properties properties = new Properties();
        final Class<? extends Annotation> annotationType = annotationAttributes.annotationType();
        Objects.requireNonNull(annotationType);
        for (Method method : annotationType.getMethods()) {
            if (method.isAnnotationPresent(Property.class)) {
                Property property = method.getAnnotation(Property.class);
                Object value = annotationAttributes.get(method.getName());
                if (value instanceof String) {
                    value = ((String) value).replace(LOGIN_TABLE_PLACE_HOLDER, logicTable);
                }
                properties.put(property.value(), value);
            }
        }

        return properties;
    }

    private void processClassBasedSharingStrategyProperties(final Properties properties) {

        if (properties.get(Property.CLASS_BASED_STRATEGY) instanceof ShardingStrategy.Strategy) {
            properties.put(Property.CLASS_BASED_STRATEGY,
                    ((ShardingStrategy.Strategy) properties.get(Property.CLASS_BASED_STRATEGY)).name());
        }

        if (properties.get(Property.CLASS_BASED_ALGORITHM_CLASS_NAME) instanceof Class) {
            properties.put(Property.CLASS_BASED_ALGORITHM_CLASS_NAME,
                    ((Class<?>) properties.get(Property.CLASS_BASED_ALGORITHM_CLASS_NAME)).getName());

        }
    }

    private Collection<Annotation> findAllShardingStrategyAnnotations(final Class<?> clazz) {

        final Collection<Annotation> annotations = new ArrayList<>();

        for (Class<? extends Annotation> annotation : SHARDING_STRATEGY_ANNOTATIONS) {
            annotations.addAll(AnnotatedElementUtils.findMergedRepeatableAnnotations(clazz, annotation));
        }

        return annotations;
    }

    private ShardingStrategyRegistration buildShardingStrategyConfiguration(final ShardingStrategy shardingStrategy,
                                                                            final String name,
                                                                            final AnnotationAttributes annotationAttributes,
                                                                            final Properties properties) {

        return new ShardingStrategyRegistration(shardingStrategy.strategy(), shardingStrategy.type(),
                name, annotationAttributes.getStringArray("columns"), properties);
    }

    private String buildShardingStrategyName(final String table, final ShardingStrategy.Scope scope,
                                             final String type) {

        return String.join("-", table, scope.name(), type);
    }

    @Override
    public void addShardingTable(final @NonNull ShardingTableRegistry registry) {

        this.tables.forEach(registry::addShardingTableRule);
    }

    @Override
    public void addBindingTables(final @NonNull BindingTableRegistry registry) {

        registry.addAllBindingTables(this.bindTables);
    }

    @Override
    public void addBroadcastTables(final @NonNull BroadcastTableRegistry registry) {

        registry.addBroadcastTables(this.broadcastTables);
    }

    @Override
    public void addShardingAlgorithms(final @NonNull ShardingAlgorithmRegistry registry) {
        for (Map.Entry<String, ShardingStrategyRegistration> entry : shardingStrategies.entrySet()) {
            registry.addShardingAlgorithm(entry.getKey(), entry.getValue().getType(), entry.getValue().getProperties());
        }
    }

}

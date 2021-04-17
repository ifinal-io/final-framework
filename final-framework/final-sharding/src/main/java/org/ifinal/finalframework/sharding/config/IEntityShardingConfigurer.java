package org.ifinal.finalframework.sharding.config;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import org.ifinal.finalframework.core.IEntity;
import org.ifinal.finalframework.io.support.ServicesLoader;
import org.ifinal.finalframework.sharding.annotation.Property;
import org.ifinal.finalframework.sharding.annotation.ShardingStrategy;
import org.ifinal.finalframework.sharding.annotation.ShardingTable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Component
public class IEntityShardingConfigurer implements ShardingConfigurer {

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

    public IEntityShardingConfigurer() {
        this.init();
    }

    private void init() {
        List<Class<?>> classes = ServicesLoader.loadClasses(IEntity.class, IEntity.class.getClassLoader());

        if (logger.isInfoEnabled()) {
            logger.info("found entities size={}", classes.size());
        }

        for (Class<?> clazz : classes) {

            if (clazz.isAnnotationPresent(ShardingTable.class)) {
                parseShardingTable(clazz);
            }

        }
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

    private void parseShardingStrategy(final Class<?> clazz, final String logicTable, final ShardingTableRegistration table,
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

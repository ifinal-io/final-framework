package org.ifinal.finalframework.sharding.config;

import org.ifinal.finalframework.annotation.core.IEntity;
import org.ifinal.finalframework.annotation.sharding.*;
import org.ifinal.finalframework.io.support.ServicesLoader;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class IEntityShardingConfigurer implements ShardingConfigurer {

    private static final String LOGIN_TABLE_PLACE_HOLDER = "${logicTable}";

    private final Collection<ShardingTableRegistration> tables = new ArrayList<>();
    private final Collection<String> bindTables = new ArrayList<>();
    private final Collection<String> broadcastTables = new ArrayList<>();
    private final Map<String, ShardingStrategyRegistration> shardingStrategies = new LinkedHashMap<>();

    private static final Set<Class<? extends Annotation>> SHARDING_STRATEGY_ANNOTATIONS =
            ServicesLoader.loadClasses(ShardingStrategy.class)
                    .stream()
                    .map(clazz -> (Class<? extends Annotation>) clazz)
                    .collect(Collectors.toSet());


    public IEntityShardingConfigurer() {
        this.init();
    }

    private void init() {
        for (Class<?> clazz : ServicesLoader.loadClasses(IEntity.class)) {

            if (clazz.isAnnotationPresent(ShardingTable.class)) {
                ShardingTable shardingTable = clazz.getAnnotation(ShardingTable.class);
                Collection<Annotation> shardingStrategyAnnotations = findAllShardingStrategyAnnotations(clazz);

                final String actualDataNodes = String.join(",", shardingTable.actualDataNodes());

                for (String logicTable : shardingTable.logicTables()) {


                    if (shardingTable.binding()) {
                        bindTables.add(logicTable);
                    }

                    if (shardingTable.broadcast()) {
                        broadcastTables.add(logicTable);
                    }

                    final ShardingTableRegistration table = new ShardingTableRegistration(logicTable, actualDataNodes.replace(LOGIN_TABLE_PLACE_HOLDER, logicTable));

                    for (Annotation annotation : shardingStrategyAnnotations) {


                        Class<? extends Annotation> annotationType = annotation.annotationType();
                        if (annotationType.isAnnotationPresent(ShardingStrategy.class)) {
                            ShardingStrategy shardingStrategy = annotationType.getAnnotation(ShardingStrategy.class);

                            AnnotationAttributes annotationAttributes = AnnotationUtils.getAnnotationAttributes(clazz, annotation);

                            ShardingScope scope = annotationAttributes.getEnum("scope");
                            final String name = buildShardingStrategyName(logicTable, scope, shardingStrategy.value());


                            final Properties properties = new Properties();

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


                            ShardingStrategyRegistration shardingStrategyRegistration = buildShardingStrategyConfiguration(shardingStrategy.value(), name, annotationAttributes, properties);
                            switch (scope) {
                                case TABLE:
                                    table.setTableShardingStrategy(shardingStrategyRegistration);
                                    break;
                                case DATABASE:
                                    table.setDatabaseShardingStrategy(shardingStrategyRegistration);
                                    break;
                                case KEY:
                                    break;
                            }


                            shardingStrategies.put(name, shardingStrategyRegistration);
                        }
                    }


                    tables.add(table);

                }

            }


        }
    }

    private Collection<Annotation> findAllShardingStrategyAnnotations(Class<?> clazz) {
        final Collection<Annotation> annotations = new ArrayList<>();

        for (Class<? extends Annotation> annotation : SHARDING_STRATEGY_ANNOTATIONS) {
            annotations.addAll(AnnotatedElementUtils.findMergedRepeatableAnnotations(clazz, annotation));
        }


        return annotations;
    }


    private ShardingStrategyRegistration buildShardingStrategyConfiguration(ShardingType type, String name, AnnotationAttributes annotationAttributes, Properties properties) {
        final String columns = String.join(",", annotationAttributes.getStringArray("columns"));
        return new ShardingStrategyRegistration(type, name, columns, properties);

    }

    private String buildShardingStrategyName(String table, ShardingScope scope, ShardingType type) {
        return String.join("-", table, scope.name(), type.name());
    }

    @Override
    public void addShardingTable(@NonNull ShardingTableRegistry registry) {
        this.tables.forEach(registry::addShardingTableRule);
    }

    @Override
    public void addBindingTables(@NonNull BindingTableRegistry registry) {
        registry.addAllBindingTables(this.bindTables);
    }

    @Override
    public void addBroadcastTables(@NonNull BroadcastTableRegistry registry) {
        registry.addBroadcastTables(this.broadcastTables);
    }

    @Override
    public void addShardingAlgorithms(@NonNull ShardingAlgorithmRegistry registry) {
        for (Map.Entry<String, ShardingStrategyRegistration> entry : shardingStrategies.entrySet()) {
            registry.addShardingAlgorithm(entry.getKey(), entry.getValue().getType(), entry.getValue().getProperties());
        }
    }
}

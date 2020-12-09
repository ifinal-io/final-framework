package org.ifinal.finalframework.sharding.config;

import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.ShardingStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
import org.ifinal.finalframework.core.annotation.IEntity;
import org.ifinal.finalframework.io.support.ServicesLoader;
import org.ifinal.finalframework.sharding.annotation.Property;
import org.ifinal.finalframework.sharding.annotation.ShardingStrategy;
import org.ifinal.finalframework.sharding.annotation.ShardingTable;
import org.ifinal.finalframework.sharding.annotation.ShardingType;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author likly
 * @version 1.0.0
 * @see org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration
 * @since 1.0.0
 */
@Component
public class IEntityShardingConfigurer implements ShardingConfigurer {

    private static final String LOGIN_TABLE_PLACE_HOLDER = "${logicTable}";

    private final Collection<ShardingTableRuleConfiguration> tables = new ArrayList<>();
    private final Map<String, ShardingSphereAlgorithmConfiguration> shardingAlgorithms = new LinkedHashMap<>();

    public IEntityShardingConfigurer() {
        this.init();
    }

    private void init() {
        for (Class<?> clazz : ServicesLoader.loadClasses(IEntity.class)) {

            if (clazz.isAnnotationPresent(ShardingTable.class)) {
                ShardingTable shardingTable = clazz.getAnnotation(ShardingTable.class);
                Annotation[] annotations = clazz.getAnnotations();

                final String actualDataNodes = String.join(",", shardingTable.actualDataNodes());

                for (String logicTable : shardingTable.logicTables()) {

                    final ShardingTableRuleConfiguration table = new ShardingTableRuleConfiguration(logicTable, actualDataNodes.replace(LOGIN_TABLE_PLACE_HOLDER, logicTable));

                    for (Annotation annotation : annotations) {
                        Class<? extends Annotation> annotationType = annotation.annotationType();
                        if (annotationType.isAnnotationPresent(ShardingStrategy.class)) {
                            ShardingStrategy shardingStrategy = annotationType.getAnnotation(ShardingStrategy.class);

                            AnnotationAttributes annotationAttributes = AnnotationUtils.getAnnotationAttributes(clazz, annotation);

                            final String name = buildShardingStrategyName(logicTable, shardingStrategy);
                            ShardingStrategyConfiguration shardingStrategyConfiguration = buildShardingStrategyConfiguration(shardingStrategy.type(), name, annotationAttributes);
                            switch (shardingStrategy.scope()) {
                                case TABLE:
                                    table.setTableShardingStrategy(shardingStrategyConfiguration);
                                    break;
                                case DATABASE:
                                    table.setDatabaseShardingStrategy(shardingStrategyConfiguration);
                                    break;
                                case KEY:
                                    break;
                            }

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

                            shardingAlgorithms.put(name, new ShardingSphereAlgorithmConfiguration(shardingStrategy.type().name(), properties));
                        }
                    }


                    tables.add(table);

                }

            }


        }
    }


    private ShardingStrategyConfiguration buildShardingStrategyConfiguration(ShardingType type, String name, AnnotationAttributes annotationAttributes) {
        final String columns = String.join(",", annotationAttributes.getStringArray("columns"));

        switch (type) {
            case INLINE:
                return new StandardShardingStrategyConfiguration(columns, name);
            default:
                throw new IllegalArgumentException(type.name());
        }


    }


    private String buildShardingStrategyName(String table, ShardingStrategy shardingStrategy) {
        return String.join("-", table, shardingStrategy.scope().name(), shardingStrategy.type().name());
    }

}

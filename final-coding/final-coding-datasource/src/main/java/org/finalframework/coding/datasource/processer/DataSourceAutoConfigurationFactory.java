package org.finalframework.coding.datasource.processer;


import org.finalframework.coding.datasource.annotation.DataSource;
import org.finalframework.coding.datasource.annotation.MapperScan;
import org.finalframework.coding.datasource.annotation.ShardingRule;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-16 17:06:47
 * @since 1.0
 */
public class DataSourceAutoConfigurationFactory {


    private final ProcessingEnvironment processingEnvironment;
    /**
     * @see ShardingRule
     */
    private final TypeElement shardingRuleTypeElement;
    private final Map<String, ExecutableElement> shardingRuleExecutableElements;
    private final ExecutableElement shardingRuleTables;

    public DataSourceAutoConfigurationFactory(ProcessingEnvironment processingEnvironment) {
        this.processingEnvironment = processingEnvironment;
        this.shardingRuleTypeElement = processingEnvironment.getElementUtils().getTypeElement(ShardingRule.class.getCanonicalName());
        this.shardingRuleExecutableElements = this.shardingRuleTypeElement.getEnclosedElements()
                .stream()
                .filter(item -> ((Element) item).getKind() == ElementKind.METHOD)
                .map(item -> ((ExecutableElement) item))
                .collect(Collectors.toMap(item -> item.getSimpleName().toString(), Function.identity()));
        this.shardingRuleTables = shardingRuleExecutableElements.get("tables");
    }

    public DataSourceAutoConfiguration create(TypeElement element) {

        final String packageName = processingEnvironment.getElementUtils().getPackageOf(element).getQualifiedName().toString();
        final String datasource = element.getSimpleName().toString();

        DataSource dataSource = element.getAnnotation(DataSource.class);

        final DataSourceAutoConfiguration configuration = new DataSourceAutoConfiguration(dataSource.prefix(), packageName, datasource + "AutoConfiguration");
        configuration.setDataSource(datasource.substring(0, 1).toLowerCase() + datasource.substring(1));
        configuration.getDataSources().addAll(Arrays.asList(dataSource.value()));

        List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
        for (AnnotationMirror annotationMirror : annotationMirrors) {
            String annotationType = annotationMirror.getAnnotationType().toString();
            if (annotationType.equals(ShardingRule.class.getCanonicalName())) {
                Map<? extends ExecutableElement, ? extends AnnotationValue> annotationElementValues = annotationMirror.getElementValues();
                List<AnnotationValue> tableTypes = (List<AnnotationValue>) annotationElementValues.get(shardingRuleTables).getValue();

                List<String> tables = tableTypes.stream()
                        .map(it -> (DeclaredType) it.getValue())
                        .map(it -> it.asElement().getSimpleName().toString())
                        .collect(Collectors.toList());

                ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
                shardingRuleConfiguration.getTables().addAll(tables);
                configuration.setShardingRule(shardingRuleConfiguration);

            }
        }

        MapperScan mapperScan = element.getAnnotation(MapperScan.class);
        if (mapperScan != null) {
            configuration.setMapperScan(buildMapperScanConfiguration(mapperScan));
        }

        return configuration;
    }

    private MapperScanConfiguration buildMapperScanConfiguration(MapperScan mapperScan) {
        MapperScanConfiguration mapperScanConfiguration = new MapperScanConfiguration();

        mapperScanConfiguration.setBasePackages(mapperScan.basePackages());
        mapperScanConfiguration.setMapperLocations(mapperScan.mapperLocations());

        return mapperScanConfiguration;
    }


}


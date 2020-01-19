package org.finalframework.coding.datasource.processer;


import org.finalframework.coding.datasource.annotation.DataSource;
import org.finalframework.coding.datasource.annotation.ShardingRule;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-16 17:06:47
 * @since 1.0
 */
public class DataSourceAutoConfigurationFactory {

    private ProcessingEnvironment processingEnvironment;

    public DataSourceAutoConfigurationFactory(ProcessingEnvironment processingEnvironment) {
        this.processingEnvironment = processingEnvironment;
    }

    public DataSourceAutoConfiguration create(TypeElement element) {

        final String packageName = processingEnvironment.getElementUtils().getPackageOf(element).getQualifiedName().toString();
        final String datasource = element.getSimpleName().toString();

        DataSource dataSource = element.getAnnotation(DataSource.class);
        ShardingRule shardingRule = element.getAnnotation(ShardingRule.class);

        final DataSourceAutoConfiguration configuration = new DataSourceAutoConfiguration(dataSource.prefix(), packageName, datasource + "AutoConfiguration");
        configuration.setDataSource(datasource.substring(0, 1).toLowerCase() + datasource.substring(1));
        configuration.getDataSources().addAll(Arrays.asList(dataSource.value()));
        return configuration;
    }


}


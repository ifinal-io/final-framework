package org.finalframework.coding.datasource.processer;


import org.finalframework.coding.generator.JavaSourceGenerator;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-17 02:09:30
 * @since 1.0
 */
public class DataSourceAutoConfigurationGenerator extends JavaSourceGenerator<DataSourceAutoConfiguration> {

    private DataSourceAutoConfigurationFactory factory;

    public DataSourceAutoConfigurationGenerator(ProcessingEnvironment processEnv, String targetRoute) {
        super(processEnv, targetRoute);
        factory = new DataSourceAutoConfigurationFactory(processEnv);
    }

    @Override
    protected DataSourceAutoConfiguration buildJavaSource(TypeElement entity) {
        return factory.create(entity);
    }


    @Override
    protected void coding(DataSourceAutoConfiguration source) {
        source.getDataSources()
                .stream()
                .filter(it -> !source.getPrefix().equals("spring.datasource"))
                .map(it -> {
                    String dataSourceName = it.substring(0, 1).toUpperCase() + it.substring(1);
                    if (dataSourceName.equalsIgnoreCase("datasource")) {
                        dataSourceName = "";
                    }
                    return DataSourceProperties.builder()
                            .prefix(source.getPrefix() + "." + it)
                            .packageName(source.getPackageName())
                            .name(dataSourceName + "DataSourceProperties")
                            .build();
                })
                .forEach(it -> {
                    try {
                        coder.coding(it, processEnv.getFiler().createSourceFile(it.getPackageName() + "." + it.getName()).openWriter());
                    } catch (Exception e) {
                        // ignore
                    }
                });
        super.coding(source);
    }
}


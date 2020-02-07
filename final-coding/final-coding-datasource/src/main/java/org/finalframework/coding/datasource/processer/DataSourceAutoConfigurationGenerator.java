package org.finalframework.coding.datasource.processer;


import org.finalframework.coding.datasource.spring.metadata.DataSourceMetaData;
import org.finalframework.coding.datasource.spring.metadata.ShardingRuleMetaData;
import org.finalframework.coding.datasource.spring.metadata.ShardingTableMetaData;
import org.finalframework.coding.generator.JavaSourceGenerator;
import org.springframework.boot.configurationprocessor.MetadataStore;
import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * @author likly
 * @version 1.0
 * @date 2020-01-17 02:09:30
 * @since 1.0
 */
public class DataSourceAutoConfigurationGenerator extends JavaSourceGenerator<DataSourceAutoConfiguration> {

    private final DataSourceAutoConfigurationFactory factory;
    private final MetadataStore metadataStore;
    private final ConfigurationMetadata metadata;

    public DataSourceAutoConfigurationGenerator(ProcessingEnvironment processEnv, String targetRoute) {
        super(processEnv, targetRoute);
        this.metadataStore = new MetadataStore(processEnv);
        this.metadata = new ConfigurationMetadata();
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
                .map(it -> new DataSourceMetaData(source.getPrefix() + ".datasource", it))
                .forEach(this.metadata::merge);

        if (source.getShardingRule() != null) {
            this.metadata.merge(new ShardingRuleMetaData(source.getPrefix() + ".sharding-rule", source.getShardingRule().getTables()));
        }


        super.coding(source);

        try {
            ConfigurationMetadata configurationMetadata = this.metadataStore.readMetadata();
            if (configurationMetadata != null) {
                this.metadata.merge(configurationMetadata);
            }
            if (!this.metadata.getItems().isEmpty()) {
                this.metadataStore.writeMetadata(this.metadata);
            }
        } catch (Exception e) {
            processEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
    }


}


package org.finalframework.coding.datasource.processer;


import org.finalframework.coding.generator.JavaSourceGenerator;
import org.finalframework.data.datasource.InlineShardingStrategyProperties;
import org.finalframework.data.datasource.TableRuleProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.configurationprocessor.MetadataStore;
import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata;
import org.springframework.boot.configurationprocessor.metadata.ItemHint;
import org.springframework.boot.configurationprocessor.metadata.ItemMetadata;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.sql.Driver;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
                .forEach(it -> {
                    this.buildDataSourceMetadata(source.getPrefix(), it);
                });

        if (source.getShardingRule() != null) {
            source.getShardingRule()
                    .getTables()
                    .forEach(it -> {
                        this.buildShardingRuleTable(source.getPrefix(), it);
                    });
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

    /**
     * {
     * "name": "final.sharding.data-source.ds0.continue-on-error",
     * "type": "java.lang.Boolean",
     * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
     * },
     * {
     * "name": "final.sharding.data-source.ds0.data",
     * "type": "java.util.List<java.lang.String>",
     * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
     * },
     * },
     * {
     * "name": "final.sharding.data-source.ds0.data-username",
     * "type": "java.lang.String",
     * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
     * },
     * {
     * "name": "final.sharding.data-source.ds0.driver-class-name",
     * "type": "java.lang.String",
     * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
     * },
     * {
     * "name": "final.sharding.data-source.ds0.generate-unique-name",
     * "type": "java.lang.Boolean",
     * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
     * },
     * {
     * "name": "final.sharding.data-source.ds0.initialization-mode",
     * "type": "org.springframework.boot.jdbc.DataSourceInitializationMode",ShardingDataSourceAutoConfiguration
     * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
     * },
     * {
     * "name": "final.sharding.data-source.ds0.jndi-name",
     * "type": "java.lang.String",
     * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
     * },
     * {
     * "name": "final.sharding.data-source.ds0.name",
     * "type": "java.lang.String",
     * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
     * },
     * {
     * "name": "final.sharding.data-source.ds0.platform",
     * "type": "java.lang.String",
     * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
     * },
     * {
     * "name": "final.sharding.data-source.ds0.schema",
     * "type": "java.util.List<java.lang.String>",
     * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
     * },
     * {
     * "name": "final.sharding.data-source.ds0.schema-password",
     * "type": "java.lang.String",
     * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
     * },
     * {
     * "name": "final.sharding.data-source.ds0.schema-username",
     * "type": "java.lang.String",
     * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
     * },
     * {
     * "name": "final.sharding.data-source.ds0.separator",
     * "type": "java.lang.String",
     * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
     * },
     * {
     * "name": "final.sharding.data-source.ds0.sql-script-encoding",
     * "type": "java.nio.charset.Charset",
     * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
     * },
     * {
     * "name": "final.sharding.data-source.ds0.type",
     * "type": "java.lang.Class<? extends javax.sql.DataSource>",
     * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
     * },
     * {
     * "name": "final.sharding.data-source.ds0.url",
     * "type": "java.lang.String",
     * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
     * },
     * {
     * "name": "final.sharding.data-source.ds0.username",
     * "type": "java.lang.String",
     * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
     * },
     * {
     * "name": "final.sharding.data-source.ds0.xa",
     * "type": "org.springframework.boot.autoconfigure.jdbc.DataSourceProperties$Xa",
     * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
     * }
     */
    private void buildDataSourceMetadata(String prefix, String datasource) {
        /**
         * {
         *  "name": "final.sharding.datasource.ds0.url",
         *  "type": "java.lang.String",
         *  "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
         *  }
         */
        this.metadata.add(ItemMetadata.newProperty(prefix + ".datasource", datasource + ".url",
                String.class.getCanonicalName(), DataSourceProperties.class.getCanonicalName(),
                null, null, null, null));
        /**
         * {
         *  "name": "final.sharding.datasource.ds0.username",
         *  "type": "java.lang.String",
         *  "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
         *  }
         */
        this.metadata.add(ItemMetadata.newProperty(prefix + ".datasource", datasource + ".username",
                String.class.getCanonicalName(), DataSourceProperties.class.getCanonicalName(),
                null, null, null, null));

        /**
         * {
         * "name": "final.sharding.datasource.ds0.password",
         * "type": "java.lang.String",
         * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
         */
        this.metadata.add(ItemMetadata.newProperty(prefix + ".datasource", datasource + ".password",
                String.class.getCanonicalName(), DataSourceProperties.class.getCanonicalName(),
                null, null, null, null));

        /**
         * {
         * "name": "final.sharding.data-source.ds0.driver-class-name",
         * "type": "java.lang.String",
         * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
         * },
         */

        this.metadata.add(ItemMetadata.newProperty(prefix + ".datasource", datasource + ".driverClassName",
                String.class.getCanonicalName(), DataSourceProperties.class.getCanonicalName(),
                null, null, null, null));

        /**
         * {
         *       "name": "final.sharding.data-source.ds0.driver-class-name",
         *       "providers": [
         *         {
         *           "name": "class-reference",
         *           "parameters": {
         *             "target": "java.sql.Driver"
         *           }
         *         }
         *       ]
         *     }
         */

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("target", Driver.class.getCanonicalName());
        this.metadata.add(new ItemHint(prefix + ".datasource." + datasource + ".driverClassName",
                null,
                Arrays.asList(
                        new ItemHint.ValueProvider("class-reference", parameters)
                )
        ));
    }

    private void buildShardingRuleTable(String prefix, String table) {
        this.metadata.add(ItemMetadata.newProperty(prefix + ".shardingRule.tables.", table + ".logicTable",
                String.class.getCanonicalName(), TableRuleProperties.class.getCanonicalName(),
                null, null, null, null));

        this.metadata.add(ItemMetadata.newProperty(prefix + ".shardingRule.tables.", table + ".actualDataNodes",
                String.class.getCanonicalName(), TableRuleProperties.class.getCanonicalName(),
                null, null, null, null));

        this.metadata.add(ItemMetadata.newProperty(prefix + ".shardingRule.tables.", table + ".databaseShardingStrategy.inline.shardingColumn",
                String.class.getCanonicalName(), InlineShardingStrategyProperties.class.getCanonicalName(),
                null, null, null, null));

        this.metadata.add(ItemMetadata.newProperty(prefix + ".shardingRule.tables.", table + ".databaseShardingStrategy.inline.algorithmExpression",
                String.class.getCanonicalName(), InlineShardingStrategyProperties.class.getCanonicalName(),
                null, null, null, null));

        this.metadata.add(ItemMetadata.newProperty(prefix + ".shardingRule.tables.", table + ".tableShardingStrategy.inline.shardingColumn",
                String.class.getCanonicalName(), InlineShardingStrategyProperties.class.getCanonicalName(),
                null, null, null, null));

        this.metadata.add(ItemMetadata.newProperty(prefix + ".shardingRule.tables.", table + ".tableShardingStrategy.inline.algorithmExpression",
                String.class.getCanonicalName(), InlineShardingStrategyProperties.class.getCanonicalName(),
                null, null, null, null));

    }
}


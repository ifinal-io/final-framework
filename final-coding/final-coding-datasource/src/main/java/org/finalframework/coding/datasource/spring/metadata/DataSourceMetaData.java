package org.finalframework.coding.datasource.spring.metadata;


import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata;
import org.springframework.boot.configurationprocessor.metadata.ItemHint;
import org.springframework.boot.configurationprocessor.metadata.ItemMetadata;

import java.sql.Driver;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
 *
 * @author likly
 * @version 1.0
 * @date 2020-02-06 17:11:44
 * @since 1.0
 */
public class DataSourceMetaData extends ConfigurationMetadata {

    private final String prefix;
    private final String datasource;

    public DataSourceMetaData(String prefix, String datasource) {
        this.prefix = prefix;
        this.datasource = datasource;
        this.init();
    }

    private void init() {
        this.url();
        this.username();
        this.password();
        this.driverClassName();
    }

    private void url() {
        /**
         * {
         *  "name": "final.sharding.datasource.ds0.url",
         *  "type": "java.lang.String",
         *  "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
         *  }
         */
        this.add(ItemMetadata.newProperty(prefix + ".", datasource + ".url",
                String.class.getCanonicalName(), DataSourceProperties.class.getCanonicalName(),
                null, "数据库连接地址", null, null));
    }

    private void username() {
        /**
         * {
         *  "name": "final.sharding.datasource.ds0.username",
         *  "type": "java.lang.String",
         *  "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
         *  }
         */
        this.add(ItemMetadata.newProperty(prefix + ".", datasource + ".username",
                String.class.getCanonicalName(), DataSourceProperties.class.getCanonicalName(),
                null, "用户名", null, null));
    }

    private void password() {
        /**
         * {
         * "name": "final.sharding.datasource.ds0.password",
         * "type": "java.lang.String",
         * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
         */
        this.add(ItemMetadata.newProperty(prefix + ".", datasource + ".password",
                String.class.getCanonicalName(), DataSourceProperties.class.getCanonicalName(),
                null, "密码", null, null));
    }

    private void driverClassName() {
        /**
         * {
         * "name": "final.sharding.data-source.ds0.driver-class-name",
         * "type": "java.lang.String",
         * "sourceType": "org.finalframework.test.dao.datasource.Ds0DataSourceProperties"
         * },
         */

        this.add(ItemMetadata.newProperty(prefix + ".", datasource + ".driverClassName",
                String.class.getCanonicalName(), DataSourceProperties.class.getCanonicalName(),
                null, "驱动名称", null, null));

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
        this.add(new ItemHint(prefix + "." + datasource + ".driverClassName",
                null,
                Arrays.asList(
                        new ItemHint.ValueProvider("class-reference", parameters)
                )
        ));
    }


}


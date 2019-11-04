package org.finalframework.coding.datasource.processer;


import org.finalframework.coding.annotation.Template;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-24 11:31:04
 * @since 1.0
 */
@Template("datasource/dataSourceProperties.vm")
public class DataSourceProperties implements Serializable {

    private final String packageName;
    private final String name;
    private final String prefix;

    private DataSourceProperties(Builder builder) {
        this.packageName = builder.packageName;
        this.name = builder.name;
        this.prefix = builder.prefix;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public static class Builder implements org.finalframework.core.Builder<DataSourceProperties> {
        private String packageName;
        private String name;
        private String prefix;

        public Builder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder prefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        @Override
        public DataSourceProperties build() {
            return new DataSourceProperties(this);
        }
    }

}


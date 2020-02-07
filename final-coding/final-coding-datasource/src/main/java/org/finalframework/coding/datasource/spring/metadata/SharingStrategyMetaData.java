package org.finalframework.coding.datasource.spring.metadata;


import org.springframework.boot.configurationprocessor.metadata.ConfigurationMetadata;

/**
 * @author likly
 * @version 1.0
 * @date 2020-02-06 18:46:25
 * @since 1.0
 */
public class SharingStrategyMetaData extends ConfigurationMetadata {
    private final String prefix;

    public SharingStrategyMetaData(String prefix) {
        this.prefix = prefix;
        this.init();
    }

    private void init() {
        this.merge(new InlineSharingStrategyMetaData(prefix + ".inline"));
    }
}


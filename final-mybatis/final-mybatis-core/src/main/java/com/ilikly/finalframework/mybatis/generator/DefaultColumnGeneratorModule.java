package com.ilikly.finalframework.mybatis.generator;

import com.ilikly.finalframework.core.PrimaryTypes;
import com.ilikly.finalframework.data.mapping.generator.ColumnGeneratorModule;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-22 13:54:38
 * @since 1.0
 */
public class DefaultColumnGeneratorModule extends ColumnGeneratorModule {

    {
        PrimaryTypes.ALL.stream().forEach(clazz -> registerColumnGenerator(clazz, null, BaseColumnGenerator.INSTANCE));
        PrimaryTypes.ALL.stream().forEach(clazz -> registerColumnGenerator(clazz, List.class, BaseColumnGenerator.INSTANCE));
        PrimaryTypes.ALL.stream().forEach(clazz -> registerColumnGenerator(clazz, Set.class, BaseColumnGenerator.INSTANCE));
        registerColumnGenerator(Date.class, null, BaseColumnGenerator.INSTANCE);
    }

}

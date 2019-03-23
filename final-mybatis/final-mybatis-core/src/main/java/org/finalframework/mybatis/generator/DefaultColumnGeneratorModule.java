package org.finalframework.mybatis.generator;

import org.finalframework.core.PrimaryTypes;
import org.finalframework.data.mapping.generator.ColumnGeneratorModule;

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
        PrimaryTypes.ALL.stream().forEach(clazz -> registerColumnGenerator(clazz, null, DefaultColumnGenerator.INSTANCE));
        PrimaryTypes.ALL.stream().forEach(clazz -> registerColumnGenerator(clazz, List.class, DefaultColumnGenerator.INSTANCE));
        PrimaryTypes.ALL.stream().forEach(clazz -> registerColumnGenerator(clazz, Set.class, DefaultColumnGenerator.INSTANCE));
        registerColumnGenerator(Date.class, null, DefaultColumnGenerator.INSTANCE);
    }

}

package org.finalframework.data.mapping.generator;

import lombok.Setter;
import org.finalframework.data.mapping.Dialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-22 09:49:58
 * @since 1.0
 */
public class ColumnGeneratorRegistry {
    private static final Logger logger = LoggerFactory.getLogger(ColumnGeneratorRegistry.class);
    private static final ColumnGeneratorRegistry INSTANCE = new ColumnGeneratorRegistry();
    private ColumnGeneratorModule columnGeneratorModule;
    @Setter
    private ColumnGenerator defaultColumnGenerator;

    public static ColumnGeneratorRegistry getInstance() {
        return INSTANCE;
    }

    public <T> ColumnGenerator getColumnGenerator(Class<T> javaType, Class<? extends Collection> collectionType) {
        ColumnGenerator columnGenerator = columnGeneratorModule.getColumnGenerator(javaType, collectionType);

        if(columnGenerator == null){
            columnGenerator = defaultColumnGenerator;
        }

        if (columnGenerator == null) {
            throw new NullPointerException(String.format("not fount columnGenerator of,javaType=%s,collectionType=%s",
                    javaType.getCanonicalName(), collectionType.getCanonicalName()));
        }
        logger.trace("find columnGenerator: ,javaType={},collectionType={},columnGenerator={}",
                javaType, collectionType, columnGenerator.getClass());
        return columnGenerator;

    }

    public <T> void registerColumnGenerator(Dialect dialect, Class<T> javaType, Class<? extends Collection> collectionType, ColumnGenerator columnGenerator) {

    }

    public void registerColumnModule(ColumnGeneratorModule module) {
        this.columnGeneratorModule = module;
    }


}

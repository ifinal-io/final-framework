package com.ilikly.finalframework.data.mapping.generator;

import com.ilikly.finalframework.data.mapping.Dialect;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-22 09:49:58
 * @since 1.0
 */
public class ColumnGeneratorRegistry {
    private static final Logger logger = LoggerFactory.getLogger(ColumnGeneratorRegistry.class);
    private static final ColumnGeneratorRegistry INSTANCE = new ColumnGeneratorRegistry();
    private final Map<Dialect, ColumnGeneratorModule> dialectColumnGeneratorModuleMap = new ConcurrentHashMap<>(8);
    @Setter
    private ColumnGenerator defaultColumnGenerator;

    public static ColumnGeneratorRegistry getInstance() {
        return INSTANCE;
    }

    public <T> ColumnGenerator getColumnGenerator(Dialect dialect, Class<T> javaType, Class<? extends Collection> collectionType) {
        ColumnGeneratorModule columnGeneratorModule = dialectColumnGeneratorModuleMap.getOrDefault(dialect, dialectColumnGeneratorModuleMap.get(Dialect.DEFAULT));
        ColumnGenerator columnGenerator = columnGeneratorModule.getColumnGenerator(javaType, collectionType);

        if (columnGenerator == null) {
            columnGenerator = dialectColumnGeneratorModuleMap.get(Dialect.DEFAULT).getColumnGenerator(javaType, collectionType);
        }

        if(columnGenerator == null){
            columnGenerator = defaultColumnGenerator;
        }

        if (columnGenerator == null) {
            throw new NullPointerException(String.format("not fount columnGenerator of dialect=%s,javaType=%s,collectionType=%s",
                    dialect.getValue().toCharArray(), javaType.getCanonicalName(), collectionType.getCanonicalName()));
        }
        logger.trace("find columnGenerator: dialect={},javaType={},collectionType={},columnGenerator={}",
                dialect,javaType,collectionType,columnGenerator.getClass());
        return columnGenerator;

    }

    public <T> void  registerColumnGenerator(Dialect dialect,Class<T> javaType, Class<? extends Collection> collectionType,ColumnGenerator columnGenerator){

    }

    public void registerColumnModule(Dialect dialect,ColumnGeneratorModule module) {
        dialectColumnGeneratorModuleMap.put(dialect, module);
    }


}

package org.ifinal.finalframework.mybatis.handler.postgre;

import org.ifinal.finalframework.annotation.data.PersistentType;
import org.ifinal.finalframework.mybatis.handler.PersistentTypeHandlerRegister;
import org.ifinal.finalframework.mybatis.handler.TypeHandlerModule;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class PGTypeHandlerModule extends TypeHandlerModule {
    public static PGTypeHandlerModule DEFAULT = new PGTypeHandlerModule() {
        {
            registerPersistentTypeHandlerRegister(PersistentType.AUTO, PersistentTypeHandlerRegister.AUTO);
            registerPersistentTypeHandlerRegister(PersistentType.JSON, new PGJsonPersistentTypeHandlerRegister());
        }
    };
}



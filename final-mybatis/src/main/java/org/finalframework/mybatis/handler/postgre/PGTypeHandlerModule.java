

package org.finalframework.mybatis.handler.postgre;

import org.finalframework.annotation.data.PersistentType;
import org.finalframework.mybatis.handler.PersistentTypeHandlerRegister;
import org.finalframework.mybatis.handler.TypeHandlerModule;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-22 13:04:19
 * @since 1.0
 */
public class PGTypeHandlerModule extends TypeHandlerModule {
    public static PGTypeHandlerModule DEFAULT = new PGTypeHandlerModule() {
        {
            registerPersistentTypeHandlerRegister(PersistentType.AUTO, PersistentTypeHandlerRegister.AUTO);
            registerPersistentTypeHandlerRegister(PersistentType.JSON, new PGJsonPersistentTypeHandlerRegister());
        }
    };
}



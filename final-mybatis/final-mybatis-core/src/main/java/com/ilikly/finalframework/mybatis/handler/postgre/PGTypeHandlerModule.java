package com.ilikly.finalframework.mybatis.handler.postgre;

import com.ilikly.finalframework.data.annotation.enums.PersistentType;
import com.ilikly.finalframework.mybatis.handler.PersistentTypeHandlerRegister;
import com.ilikly.finalframework.mybatis.handler.TypeHandlerModule;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-22 13:04:19
 * @since 1.0
 */
public class PGTypeHandlerModule extends TypeHandlerModule {
    public static PGTypeHandlerModule DEFAULT = new PGTypeHandlerModule(){
        {
            registerPersistentTypeHandlerRegister(PersistentType.AUTO,PersistentTypeHandlerRegister.AUTO);
            registerPersistentTypeHandlerRegister(PersistentType.JSON,new PGJsonPersistentTypeHandlerRegister());
        }
    };
}



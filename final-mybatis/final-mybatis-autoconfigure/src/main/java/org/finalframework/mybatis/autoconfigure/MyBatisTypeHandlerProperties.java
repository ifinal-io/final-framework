package org.finalframework.mybatis.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.TypeHandler;
import org.finalframework.mybatis.handler.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-22 09:46:59
 * @since 1.0
 */
@ConfigurationProperties(prefix = MyBatisTypeHandlerProperties.TYPE_HANDLER_PROPERTIES)
@EnableConfigurationProperties(MyBatisTypeHandlerProperties.class)
public class MyBatisTypeHandlerProperties {
    static final String TYPE_HANDLER_PROPERTIES = "final.mybatis.type-handler";

    private static final JsonTypeHandlers JSON_TYPE_HANDLERS = new JsonTypeHandlers(JsonObjectTypeHandler.class, JsonListTypeHandler.class, JsonSetTypeHandler.class);
    private static final JsonTypeHandlers JSON_BLOB_TYPE_HANDLERS = new JsonTypeHandlers(JsonObjectBlobTypeHandler.class, JsonListBlobTypeHandler.class, JsonSetBlobTypeHandler.class);


    @Getter
    @Setter
    private Class<? extends TypeHandler> enumTypeHandler = EnumTypeHandler.class;

    @Getter
    @Setter
    private Class<? extends TypeHandler> listTypeHandler = DefaultListTypeHandler.class;
    @Getter
    @Setter
    private Class<? extends TypeHandler> setTypeHandler = DefaultSetTypeHandler.class;

    @Getter
    @Setter
    private JsonTypeHandlers jsonTypeHandlers = JSON_TYPE_HANDLERS;

    @Getter
    @Setter
    private JsonTypeHandlers jsonBlobTypeHandlers = JSON_BLOB_TYPE_HANDLERS;


    @Setter
    @Getter
    public static class JsonTypeHandlers {
        private Class<? extends TypeHandler> object;
        private Class<? extends TypeHandler> list;
        private Class<? extends TypeHandler> set;

        public JsonTypeHandlers() {
        }

        public JsonTypeHandlers(Class<? extends TypeHandler> object, Class<? extends TypeHandler> list, Class<? extends TypeHandler> set) {
            this.object = object;
            this.list = list;
            this.set = set;
        }

    }

}

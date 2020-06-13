/*
 * Copyright (c) 2018-2020.  the original author or authors.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.finalframework.coding.mapper.handler;

import org.finalframework.core.configuration.Configuration;
import org.finalframework.mybatis.handler.*;

/**
 * @author likly
 * @version 1.0
 * @date 2019-10-22 10:04:19
 * @since 1.0
 */
public class TypeHandlerRegistry {

    private static final String ENUM_TYPE_HANDLER = "final.mybatis.type-handler.enum-type-handler";
    private static final String LIST_TYPE_HANDLER = "final.mybatis.type-handler.list-type-handler";
    private static final String SET_TYPE_HANDLER = "final.mybatis.type-handler.set-type-handler";
    private static final String JSON_OBJECT_TYPE_HANDLER = "final.mybatis.type-handler.json-type-handlers.object";
    private static final String JSON_LIST_TYPE_HANDLER = "final.mybatis.type-handler.json-type-handlers.list";
    private static final String JSON_SET_TYPE_HANDLER = "final.mybatis.type-handler.json-type-handlers.set";
    private static final String JSON_OBJECT_BLOB_TYPE_HANDLER = "final.mybatis.type-handler.json-blob-type-handlers.object";
    private static final String JSON_LIST_BLOB_TYPE_HANDLER = "final.mybatis.type-handler.json-blob-type-handlers.list";
    private static final String JSON_SET_BLOB_TYPE_HANDLER = "final.mybatis.type-handler.json-blob-type-handlers.set";
    private static final TypeHandlerRegistry instance = new TypeHandlerRegistry();

    private final String enumTypeHandler;
    private final String listTypeHandler;
    private final String setTypeHandler;
    private final JsonTypeHandlers jsonTypesHandlers;
    private final JsonTypeHandlers jsonBlobTypeHandlers;

    private TypeHandlerRegistry() {
        Configuration configuration = Configuration.getInstance();
        this.enumTypeHandler = configuration.getString(ENUM_TYPE_HANDLER, EnumTypeHandler.class.getCanonicalName());
        this.listTypeHandler = configuration.getString(LIST_TYPE_HANDLER, DefaultListTypeHandler.class.getCanonicalName());
        this.setTypeHandler = configuration.getString(SET_TYPE_HANDLER, DefaultSetTypeHandler.class.getCanonicalName());


        this.jsonTypesHandlers = new JsonTypeHandlers(
                configuration.getString(JSON_OBJECT_TYPE_HANDLER, JsonObjectTypeHandler.class.getCanonicalName()),
                configuration.getString(JSON_LIST_TYPE_HANDLER, JsonListTypeHandler.class.getCanonicalName()),
                configuration.getString(JSON_SET_TYPE_HANDLER, JsonSetTypeHandler.class.getCanonicalName())
        );

        this.jsonBlobTypeHandlers = new JsonTypeHandlers(
                configuration.getString(JSON_OBJECT_BLOB_TYPE_HANDLER, JsonObjectTypeHandler.class.getCanonicalName()),
                configuration.getString(JSON_LIST_BLOB_TYPE_HANDLER, JsonListBlobTypeHandler.class.getCanonicalName()),
                configuration.getString(JSON_SET_BLOB_TYPE_HANDLER, JsonSetBlobTypeHandler.class.getCanonicalName())
        );

    }

    public static TypeHandlerRegistry getInstance() {
        return instance;
    }

    public String getEnumTypeHandler() {
        return enumTypeHandler;
    }

    public String getListTypeHandler() {
        return listTypeHandler;
    }

    public String getSetTypeHandler() {
        return setTypeHandler;
    }

    public JsonTypeHandlers getJsonTypesHandlers() {
        return jsonTypesHandlers;
    }

    public JsonTypeHandlers getJsonBlobTypeHandlers() {
        return jsonBlobTypeHandlers;
    }

    public static class JsonTypeHandlers {
        private final String object;
        private final String list;
        private final String set;

        private JsonTypeHandlers(String object, String list, String set) {
            this.object = object;
            this.list = list;
            this.set = set;
        }

        public String getObject() {
            return object;
        }

        public String getList() {
            return list;
        }

        public String getSet() {
            return set;
        }
    }


}

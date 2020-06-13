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

package org.finalframework.mybatis.sql;


import org.apache.ibatis.type.TypeHandler;
import org.finalframework.data.annotation.Json;
import org.finalframework.data.mapping.Property;
import org.finalframework.data.query.QEntity;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.repository.Repository;
import org.finalframework.data.repository.RepositoryHolder;
import org.finalframework.mybatis.handler.JsonObjectTypeHandler;
import org.finalframework.mybatis.mapper.AbsMapper;
import org.finalframework.mybatis.sql.provider.ScriptSqlProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-03 21:58:33
 * @see org.finalframework.mybatis.mapper.AbsMapper
 * @since 1.0
 */
public interface AbsMapperSqlProvider extends ScriptSqlProvider {

    default Class<?> getEntityClass(Class<?> mapper) {
        final Type[] interfaces = mapper.getGenericInterfaces();
        for (Type type : interfaces) {
            if (type instanceof ParameterizedType && Repository.class
                    .isAssignableFrom((Class) ((ParameterizedType) type).getRawType())) {
                return (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[1];

            }
        }

        throw new IllegalArgumentException("can not find entity from mapper of " + mapper.getCanonicalName());
    }

    default Class<? extends TypeHandler> getTypeHandler(QProperty<?> property) {
        final Class<? extends TypeHandler<?>> typeHandler = property.getTypeHandler();
        if (typeHandler == null) {
            final Property propertyProperty = property.getProperty();
            if (propertyProperty.hasAnnotation(org.finalframework.data.annotation.TypeHandler.class)) {
                return propertyProperty.getRequiredAnnotation(org.finalframework.data.annotation.TypeHandler.class).value();
            }

            if (propertyProperty.hasAnnotation(Json.class) || propertyProperty.isCollectionLike()) {
                return JsonObjectTypeHandler.class;
            }

        }

        return typeHandler;
    }

    default Node trim(Document document, String prefix, String suffix) {
        final Element trim = document.createElement("trim");
        if (prefix != null) {
            trim.setAttribute("prefix", prefix);
        }
        if (suffix != null) {
            trim.setAttribute("suffix", suffix);
        }
        return trim;
    }

    default Node table(Document document, QEntity<?, ?> entity) {
        return cdata(document, "${table}");
    }

    default Node cdata(Document document, String data) {
        return document.createCDATASection(data);
    }

    default Node text(Document document, String data) {
        return document.createTextNode(data);
    }

    default Node where(Document document, Node... whens) {
        final Element where = document.createElement("where");

        final Element choose = document.createElement("choose");
        for (Node when : whens) {
            choose.appendChild(when);
        }
        where.appendChild(choose);

        return where;
    }

    default Node whereIdsNotNull(Document document) {
        final Element when = document.createElement("when");
        when.setAttribute("test", "ids != null");
        when.appendChild(text(document, "${properties.idProperty.column}"));
        final Element ifIdPropertyNotNull = document.createElement("if");
        ifIdPropertyNotNull.setAttribute("test", "properties.idProperty != null");
        ifIdPropertyNotNull.appendChild(text(document, "${properties.idProperty.column}"));
        when.appendChild(ifIdPropertyNotNull);


        final Node in = trim(document, "IN", null);
//        in.appendChild(text(document, "${properties.idProperty.column}"));

        final Element foreach = document.createElement("foreach");

        foreach.setAttribute("collection", "ids");
        foreach.setAttribute("item", "id");
        foreach.setAttribute("open", "(");
        foreach.setAttribute("close", ")");
        foreach.appendChild(cdata(document, "#{id}"));

        in.appendChild(foreach);

        when.appendChild(in);

        return when;
    }


}


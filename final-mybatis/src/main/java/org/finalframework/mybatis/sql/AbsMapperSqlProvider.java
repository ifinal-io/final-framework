package org.finalframework.mybatis.sql;


import org.apache.ibatis.type.TypeHandler;
import org.finalframework.annotation.data.Json;
import org.finalframework.data.mapping.Property;
import org.finalframework.data.query.QEntity;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.repository.Repository;
import org.finalframework.mybatis.handler.JsonObjectTypeHandler;
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
            if (propertyProperty.isAnnotationPresent(org.finalframework.annotation.data.TypeHandler.class)) {
                return propertyProperty.getRequiredAnnotation(org.finalframework.annotation.data.TypeHandler.class).value();
            }

            if (propertyProperty.isAnnotationPresent(Json.class) || propertyProperty.isCollectionLike()) {
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

    default String whereIdNotNull() {
        return "<where>${properties.idProperty.column} = #{id}</where>";
    }

    default String whereIdsNotNull() {
        return "<where>${properties.idProperty.column}<foreach collection=\"ids\" item=\"id\" open=\" IN (\" separator=\",\" close=\")\">#{id}</foreach></where>";
    }


}


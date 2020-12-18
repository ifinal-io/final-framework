package org.ifinal.finalframework.mybatis.sql;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.ifinal.finalframework.annotation.core.IEntity;
import org.ifinal.finalframework.data.query.QueryProvider;
import org.ifinal.finalframework.data.query.sql.AnnotationQueryProvider;
import org.ifinal.finalframework.data.repository.Repository;
import org.ifinal.finalframework.mybatis.sql.provider.ScriptSqlProvider;

/**
 * @author likly
 * @version 1.0.0
 * @see org.ifinal.finalframework.mybatis.mapper.AbsMapper
 * @since 1.0.0
 */
public interface AbsMapperSqlProvider extends ScriptSqlProvider {

    default Class<?> getEntityClass(final Class<?> mapper) {

        final Type[] interfaces = mapper.getGenericInterfaces();
        for (Type type : interfaces) {
            if (type instanceof ParameterizedType && Repository.class
                .isAssignableFrom((Class) ((ParameterizedType) type).getRawType())) {
                return (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[1];

            }
        }

        throw new IllegalArgumentException("can not find entity from mapper of " + mapper.getCanonicalName());
    }

    default QueryProvider query(String expression, Class<? extends IEntity> entity, Class<?> query) {
        return new AnnotationQueryProvider(expression, entity, query);
    }

    default String whereIdNotNull() {
        return "<where>${properties.idProperty.column} = #{id}</where>";
    }

    default String whereIdsNotNull() {
        return "<where>"
            + "${properties.idProperty.column}"
            + "<foreach collection=\"ids\" item=\"id\" open=\" IN (\" separator=\",\" close=\")\">#{id}</foreach>"
            + "</where>";
    }

}


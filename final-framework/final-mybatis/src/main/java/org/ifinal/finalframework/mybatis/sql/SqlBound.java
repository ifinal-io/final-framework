package org.ifinal.finalframework.mybatis.sql;

import lombok.Data;
import org.apache.ibatis.type.TypeHandler;
import org.ifinal.finalframework.core.annotation.IEntity;
import org.ifinal.finalframework.core.annotation.IQuery;

import java.io.Serializable;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @see org.apache.ibatis.mapping.BoundSql
 * @since 1.0.0
 */
@Data
public class SqlBound implements Serializable {
    private static final long serialVersionUID = 641816143590205180L;
    private Class<? extends IEntity<?>> entity;
    private Class<? extends IQuery> query;
    private String script;
    private String sql;
    private List<ParameterMapping> parameterMappings;

    @Data
    public static class ParameterMapping implements Serializable {
        private static final long serialVersionUID = 8682535333697518308L;
        private String property;
        private String expression;
        private Class<?> javaType;
        private Class<? extends TypeHandler<?>> typeHandler;
        private Object value;
    }

}

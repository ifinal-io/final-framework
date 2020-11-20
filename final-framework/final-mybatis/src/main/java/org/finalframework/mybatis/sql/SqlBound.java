package org.finalframework.mybatis.sql;

import lombok.Data;
import org.apache.ibatis.type.TypeHandler;
import org.finalframework.annotation.IEntity;
import org.finalframework.annotation.IQuery;

import java.io.Serializable;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/20 15:35:04
 * @see org.apache.ibatis.mapping.BoundSql
 * @since 1.0
 */
@Data
public class SqlBound implements Serializable {
    private Class<? extends IEntity<?>> entity;
    private IQuery query;
    private String script;
    private String sql;
    private List<ParameterMapping> parameterMappings;

    @Data
    public static class ParameterMapping {
        private String property;
        private String expression;
        private Class<?> javaType;
        private Class<? extends TypeHandler<?>> typeHandler;
        private Object value;
    }

}

package cn.com.likly.finalframework.mybatis.holder;

import cn.com.likly.finalframework.mybatis.annotation.enums.JdbcType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.ibatis.type.TypeHandler;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 22:05
 * @since 1.0
 */
@Data
@Builder
@AllArgsConstructor
public class PropertyHolder {
    private final String table;
    private final String property;
    private final String column;
    private final Class<?> javaType;
    private final JdbcType jdbcType;
    private final TypeHandler<?> typeHandler;
    private final Boolean primaryKey;
    private final Boolean notnull;
    private final Boolean unique;
    private final Boolean insert;
    private final Boolean update;
    private final Boolean select;
    private final String description;

    public final String getJavaTypeHandler() {
        return String.format(",javaType=%s,typeHandler=%s", javaType.getName(), typeHandler.getClass().getName());
    }

    public final String getSelectColumn() {
        return String.format("%s.%s", table, column);
    }

}

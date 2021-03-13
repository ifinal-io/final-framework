package org.ifinal.finalframework.mybatis.handler;

import org.ifinal.finalframework.annotation.core.IEnum;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.NonNull;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * 枚举类型映射器,实现将实现了{@link IEnum}接口的枚举类型持久化到数据库时，将{@link IEnum#getCode()}所返回的枚举码持久化到数据库中， 当从数据库读取时，又将枚举码转换成其对应的枚举值。 使用方法： 1. 替换默认的枚举类型映射器
 * {@link org.apache.ibatis.session.Configuration#setDefaultEnumTypeHandler(Class)} 2. mapper 中指定 typeHandler
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class EnumTypeHandler<E extends IEnum<?>> extends BaseTypeHandler<E> {

    private final Class<E> type;

    private final Map<String, E> cache;

    public EnumTypeHandler(final @NonNull Class<E> type) {
        if (!type.isEnum()) {
            throw new IllegalArgumentException(" the type must be a enum type!");
        }
        this.type = type;
        this.cache = Arrays.stream(type.getEnumConstants()).collect(Collectors.toMap(it -> it.getCode().toString(), Function.identity()));
    }

    @Override
    public void setNonNullParameter(final PreparedStatement ps, final int i, final E parameter, final JdbcType jdbcType)
        throws SQLException {
        ps.setObject(i, parameter.getCode());
    }

    @Override
    public E getNullableResult(final ResultSet rs, final String columnName) throws SQLException {
        return cache.get(rs.getString(columnName));
    }

    @Override
    public E getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {
        return cache.get(rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {
        return cache.get(cs.getString(columnIndex));
    }

}

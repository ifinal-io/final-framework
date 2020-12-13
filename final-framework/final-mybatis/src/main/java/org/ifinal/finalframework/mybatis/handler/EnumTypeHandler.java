package org.ifinal.finalframework.mybatis.handler;

import lombok.NonNull;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.ifinal.finalframework.annotation.core.IEnum;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 枚举类型映射器,实现将实现了{@link IEnum}接口的枚举类型持久化到数据库时，将{@link IEnum#getCode()}所返回的枚举码持久化到数据库中，
 * 当从数据库读取时，又将枚举码转换成其对应的枚举值。
 * <p>
 * 使用方法：
 * 1. 替换默认的枚举类型映射器 {@link org.apache.ibatis.session.Configuration#setDefaultEnumTypeHandler(Class)}
 * 2. mapper 中指定 typeHandler
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class EnumTypeHandler<E extends IEnum<?>> extends BaseTypeHandler<E> {

    private final Class<E> type;

    public EnumTypeHandler(final @NonNull Class<E> type) {

        if (!type.isEnum()) {
            throw new IllegalArgumentException(" the type must be a enum type!");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(final PreparedStatement ps, final int i, final E parameter, final JdbcType jdbcType) throws SQLException {

        Object code = parameter.getCode();

        if (code instanceof String) {
            ps.setString(i, (String) code);
        } else if (code instanceof Byte) {
            ps.setByte(i, (Byte) code);
        } else if (code instanceof Short) {
            ps.setShort(i, (Short) code);
        } else if (code instanceof Integer) {
            ps.setInt(i, (Integer) code);
        } else if (code instanceof Long) {
            ps.setLong(i, (Long) code);
        } else if (code instanceof Boolean) {
            ps.setBoolean(i, (Boolean) code);
        }
    }

    @Override
    public E getNullableResult(final ResultSet rs, final String columnName) throws SQLException {

        return IEnum.valueOf(type, rs.getString(columnName));
    }

    @Override
    public E getNullableResult(final ResultSet rs, final int columnIndex) throws SQLException {

        return IEnum.valueOf(type, rs.getString(columnIndex));
    }

    @Override
    public E getNullableResult(final CallableStatement cs, final int columnIndex) throws SQLException {

        return IEnum.valueOf(type, cs.getString(columnIndex));
    }

}

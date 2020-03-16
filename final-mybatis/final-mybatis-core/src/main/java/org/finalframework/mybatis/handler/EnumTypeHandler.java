package org.finalframework.mybatis.handler;

import lombok.NonNull;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.finalframework.core.PrimaryTypes;
import org.finalframework.data.annotation.IEnum;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * 枚举类型映射器,实现将实现了{@link IEnum}接口的枚举类型持久化到数据库时，将{@link IEnum#getCode()}所返回的枚举码持久化到数据库中，
 * 当从数据库读取时，又将枚举码转换成其对应的枚举值。
 * <p>
 * 使用方法：
 * 1. 替换默认的枚举类型映射器 {@link org.apache.ibatis.session.Configuration#setDefaultEnumTypeHandler(Class)}
 * 2. mapper 中指定 typeHandler
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-26 23:25
 * @since 1.0
 */
public class EnumTypeHandler<E extends IEnum<?>> extends BaseTypeHandler<E> {
    private final Class<E> type;

    public EnumTypeHandler(@NonNull Class<E> type) {
        if (!type.isEnum()) {
            throw new IllegalArgumentException(" the type must be a enum type!");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        Class<?> codeType = getEnumEntityCodeType();
        if (PrimaryTypes.isByte(codeType)) ps.setByte(i, (Byte) parameter.getCode());
        if (PrimaryTypes.isShort(codeType)) ps.setShort(i, (Short) parameter.getCode());
        if (PrimaryTypes.isInteger(codeType)) ps.setInt(i, (Integer) parameter.getCode());
        if (PrimaryTypes.isLong(codeType)) ps.setLong(i, (Long) parameter.getCode());
        if (PrimaryTypes.isBoolean(codeType)) ps.setBoolean(i, (Boolean) parameter.getCode());
        if (PrimaryTypes.isString(codeType)) ps.setString(i, (String) parameter.getCode());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getResult(getCode(rs, columnName));
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getResult(getCode(rs, columnIndex));
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getResult(getCode(cs, columnIndex));
    }

    private E getResult(Object code) {
        for (E enumConstant : type.getEnumConstants()) {
            if (Objects.equals(enumConstant.getCode(), code)) {
                return enumConstant;
            }
        }
        return null;
    }

    @SuppressWarnings("all")
    private Object getCode(ResultSet rs, String columnName) throws SQLException {
        Class<?> codeType = getEnumEntityCodeType();
        if (PrimaryTypes.isByte(codeType)) return rs.getByte(columnName);
        if (PrimaryTypes.isShort(codeType)) return rs.getShort(columnName);
        if (PrimaryTypes.isInteger(codeType)) return rs.getInt(columnName);
        if (PrimaryTypes.isLong(codeType)) return rs.getLong(columnName);
        if (PrimaryTypes.isBoolean(codeType)) return rs.getBoolean(columnName);
        if (PrimaryTypes.isString(codeType)) return rs.getString(columnName);
        throw new UnsupportedOperationException("");
    }

    @SuppressWarnings("all")
    private Object getCode(ResultSet rs, int columnIndex) throws SQLException {
        Class<?> codeType = getEnumEntityCodeType();
        if (PrimaryTypes.isByte(codeType)) return rs.getByte(columnIndex);
        if (PrimaryTypes.isShort(codeType)) return rs.getShort(columnIndex);
        if (PrimaryTypes.isInteger(codeType)) return rs.getInt(columnIndex);
        if (PrimaryTypes.isLong(codeType)) return rs.getLong(columnIndex);
        if (PrimaryTypes.isBoolean(codeType)) return rs.getBoolean(columnIndex);
        if (PrimaryTypes.isString(codeType)) return rs.getString(columnIndex);
        throw new UnsupportedOperationException("");
    }

    @SuppressWarnings("all")
    private Object getCode(CallableStatement cs, int columnIndex) throws SQLException {
        Class<?> codeType = getEnumEntityCodeType();
        if (PrimaryTypes.isByte(codeType)) return cs.getByte(columnIndex);
        if (PrimaryTypes.isShort(codeType)) return cs.getShort(columnIndex);
        if (PrimaryTypes.isInteger(codeType)) return cs.getInt(columnIndex);
        if (PrimaryTypes.isLong(codeType)) return cs.getLong(columnIndex);
        if (PrimaryTypes.isBoolean(codeType)) return cs.getBoolean(columnIndex);
        if (PrimaryTypes.isString(codeType)) return cs.getString(columnIndex);
        throw new UnsupportedOperationException("");
    }


    private Class<?> getEnumEntityCodeType() {
        if (type.isEnum()) {
            E[] enumConstants = type.getEnumConstants();
            if (enumConstants.length == 0) {
                throw new IllegalArgumentException(String.format("the enum type of %s didn't a any element!", type.getName()));
            }
            return enumConstants[0].getCode().getClass();
        }
        throw new IllegalArgumentException(" the type must be a enum type!");
    }
}

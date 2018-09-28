package cn.com.likly.finalframework.mybatis.handler;

import cn.com.likly.finalframework.context.entity.enums.EnumEntity;
import lombok.NonNull;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 23:25
 * @since 1.0
 */
public class EnumEntityTypeHandler<E extends EnumEntity<?>> extends BaseTypeHandler<E> {
    private final Class<E> type;

    public EnumEntityTypeHandler(@NonNull Class<E> type) {
        if (!type.isEnum()) {
            throw new IllegalArgumentException(" the type must be a enum type!");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        Class<?> codeType = getEnumEntityCodeType();
        if (JavaType.isByte(codeType)) ps.setByte(i, (Byte) parameter.getCode());
        if (JavaType.isShort(codeType)) ps.setShort(i, (Short) parameter.getCode());
        if (JavaType.isInt(codeType)) ps.setInt(i, (Integer) parameter.getCode());
        if (JavaType.isLong(codeType)) ps.setLong(i, (Long) parameter.getCode());
        if (JavaType.isBoolean(codeType)) ps.setBoolean(i, (Boolean) parameter.getCode());
        if (JavaType.isString(codeType)) ps.setString(i, (String) parameter.getCode());
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
        if (JavaType.isByte(codeType)) return rs.getByte(columnName);
        if (JavaType.isShort(codeType)) return rs.getShort(columnName);
        if (JavaType.isInt(codeType)) return rs.getInt(columnName);
        if (JavaType.isLong(codeType)) return rs.getLong(columnName);
        if (JavaType.isBoolean(codeType)) return rs.getBoolean(columnName);
        if (JavaType.isString(codeType)) return rs.getString(columnName);
        throw new UnsupportedOperationException("");
    }

    @SuppressWarnings("all")
    private Object getCode(ResultSet rs, int columnIndex) throws SQLException {
        Class<?> codeType = getEnumEntityCodeType();
        if (JavaType.isByte(codeType)) return rs.getByte(columnIndex);
        if (JavaType.isShort(codeType)) return rs.getShort(columnIndex);
        if (JavaType.isInt(codeType)) return rs.getInt(columnIndex);
        if (JavaType.isLong(codeType)) return rs.getLong(columnIndex);
        if (JavaType.isBoolean(codeType)) return rs.getBoolean(columnIndex);
        if (JavaType.isString(codeType)) return rs.getString(columnIndex);
        throw new UnsupportedOperationException("");
    }

    @SuppressWarnings("all")
    private Object getCode(CallableStatement cs, int columnIndex) throws SQLException {
        Class<?> codeType = getEnumEntityCodeType();
        if (JavaType.isByte(codeType)) return cs.getByte(columnIndex);
        if (JavaType.isShort(codeType)) return cs.getShort(columnIndex);
        if (JavaType.isInt(codeType)) return cs.getInt(columnIndex);
        if (JavaType.isLong(codeType)) return cs.getLong(columnIndex);
        if (JavaType.isBoolean(codeType)) return cs.getBoolean(columnIndex);
        if (JavaType.isString(codeType)) return cs.getString(columnIndex);
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

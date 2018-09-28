package cn.com.likly.finalframework.mybatis.handler;

import cn.com.likly.finalframework.mybatis.annotation.enums.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 13:01
 * @since 1.0
 */
public interface TypeHandlerRegistry {

    void setDefaultEnumTypeHandler(Class<? extends TypeHandler> typeHandler);

    void register(Class javaType, Class collectionType, JdbcType jdbcType, TypeHandler typeHandler);

    TypeHandler getTypeHandler(Class javaType, Class collectionType, JdbcType jdbcType);

}

package org.ifinal.finalframework.mybatis.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.lang.annotation.Annotation;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public enum MethodMethod {
    INSERT("insert", InsertProvider.class),
    REPLACE("replace", InsertProvider.class),
    SAVE("save", InsertProvider.class),
    UPDATE("update", UpdateProvider.class),
    DELETE("delete", DeleteProvider.class),
    SELECT("select", SelectProvider.class),
    SELECT_ONE("selectOne", SelectProvider.class),
    SELECT_IDS("selectIds", SelectProvider.class),
    SELECT_COUNT("selectCount", SelectProvider.class),
    TRUNCATE("truncate", UpdateProvider.class),

    ;

    private final String method;
    private final Class<? extends Annotation> annotation;

    MethodMethod(String method, Class<? extends Annotation> annotation) {
        this.method = method;
        this.annotation = annotation;
    }

}

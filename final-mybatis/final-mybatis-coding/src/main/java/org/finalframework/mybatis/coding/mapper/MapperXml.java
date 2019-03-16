package org.finalframework.mybatis.coding.mapper;

import org.finalframework.coding.annotation.Template;

import java.io.Serializable;

/**
 * <mapper namespace="${mapper}">
 * </mapper>
 *
 * @author likly
 * @version 1.0
 * @date 2018-12-23 23:01:47
 * @since 1.0
 */
@Template("mapper/mapper.xml")
public class MapperXml implements Serializable {
    private final String mapper;

    public MapperXml(String mapper) {
        this.mapper = mapper;
    }

    public String getMapper() {
        return mapper;
    }
}

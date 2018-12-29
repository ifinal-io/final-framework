package com.ilikly.finalframework.coding.plugins.mybatis.processer;

import com.ilikly.finalframework.coding.annotation.Template;

import java.io.Serializable;

/**
 * <mapper namespace="${mapper}">
 * </mapper>
 * @author likly
 * @version 1.0
 * @date 2018-12-23 23:01:47
 * @since 1.0
 */
@Template("mybatis/mapper.xml")
public class MapperXml implements Serializable {
    private final String mapper;

    public MapperXml(String mapper) {
        this.mapper = mapper;
    }

    public String getMapper() {
        return mapper;
    }
}

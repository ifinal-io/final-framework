package cn.com.likly.finalframework.coding.plugins.mybatis;

import cn.com.likly.finalframework.coding.annotation.Template;

import java.io.Serializable;

/**
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

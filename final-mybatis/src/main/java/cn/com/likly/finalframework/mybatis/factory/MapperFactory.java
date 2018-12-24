package cn.com.likly.finalframework.mybatis.factory;

import cn.com.likly.finalframework.mybatis.model.Mapper;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-22 01:02:35
 * @since 1.0
 */
public interface MapperFactory {
    Mapper create(Class mapperClass);
}

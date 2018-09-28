package cn.com.likly.finalframework.mybatis.configurer;

import cn.com.likly.finalframework.mybatis.converter.NameConverter;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-26 22:26
 * @since 1.0
 */
public interface NameConverterConfigurer {

    NameConverter getTableNameConverter();

    void setTableNameConverter(NameConverter nameConverter);

    NameConverter getPropertyNameConverter();

    void setPropertyNameConverter(NameConverter nameConverter);

}

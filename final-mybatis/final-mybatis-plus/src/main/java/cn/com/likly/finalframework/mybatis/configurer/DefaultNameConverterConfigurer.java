package cn.com.likly.finalframework.mybatis.configurer;

import cn.com.likly.finalframework.mybatis.converter.DefaultNameConverter;
import cn.com.likly.finalframework.mybatis.converter.NameConverter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:09
 * @since 1.0
 */
@Component
public class DefaultNameConverterConfigurer implements NameConverterConfigurer {

    private NameConverter tableNameConverter;
    private NameConverter propertyNameConverter;

    @PostConstruct
    private void init() {
        DefaultNameConverter nameConverter = new DefaultNameConverter();
        setTableNameConverter(nameConverter);
        setPropertyNameConverter(nameConverter);
    }

    @Override
    public NameConverter getTableNameConverter() {
        return tableNameConverter;
    }

    @Override
    public void setTableNameConverter(NameConverter nameConverter) {
        this.tableNameConverter = nameConverter;
    }

    @Override
    public NameConverter getPropertyNameConverter() {
        return propertyNameConverter;
    }

    @Override
    public void setPropertyNameConverter(NameConverter nameConverter) {
        this.propertyNameConverter = nameConverter;
    }
}

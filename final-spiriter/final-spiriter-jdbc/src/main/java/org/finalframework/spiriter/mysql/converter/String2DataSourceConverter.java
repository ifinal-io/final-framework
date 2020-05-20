package org.finalframework.spiriter.mysql.converter;

import org.finalframework.spiriter.mysql.service.DataSourceService;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.finalframework.core.converter.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-19 12:57:05
 * @since 1.0
 */
@SpringComponent
@Component
public class String2DataSourceConverter implements Converter<String, DataSource> {

    public static final Logger logger = LoggerFactory.getLogger(String2DataSourceConverter.class);

    @Resource
    private DataSourceService dataSourceService;

    @Override
    public DataSource convert(String source) {
        return dataSourceService.getDataSource(source);
    }
}

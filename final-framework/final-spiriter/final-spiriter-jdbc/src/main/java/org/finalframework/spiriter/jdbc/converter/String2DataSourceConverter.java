package org.ifinal.finalframework.spiriter.jdbc.converter;

import org.ifinal.finalframework.spiriter.jdbc.service.DataSourceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ifinal.finalframework.core.converter.Converter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;


/**
 * @author likly
 * @version 1.0.0
 *
 * @since 1.0.0
 */
 @Component
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

package org.finalframework.spiriter.mysql.service.impl;

import org.finalframework.spiriter.mysql.service.DataSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.Map;


/**
 * @author likly
 * @version 1.0
 * @date 2020-05-19 12:37:39
 * @since 1.0
 */
@Service
public class DataSourceServiceImpl implements DataSourceService, ApplicationContextAware {
    public static final Logger logger = LoggerFactory.getLogger(DataSourceServiceImpl.class);

    private Map<String, DataSource> dataSourceMap;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.dataSourceMap = applicationContext.getBeansOfType(DataSource.class);
    }

    @Override
    public Map<String, DataSource> getDataSources() {
        return dataSourceMap;
    }
}


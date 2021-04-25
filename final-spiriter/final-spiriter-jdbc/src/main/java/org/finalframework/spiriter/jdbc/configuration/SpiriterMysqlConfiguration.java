package org.ifinal.finalframework.spiriter.jdbc.configuration;


import org.apache.ibatis.session.Configuration;
import org.ifinal.finalframework.spiriter.jdbc.dao.mapper.ColumnsMapper;
import org.ifinal.finalframework.spiriter.jdbc.dao.mapper.CommonMapper;
import org.ifinal.finalframework.spiriter.jdbc.dao.mapper.TablesMapper;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;

/**
 * @author likly
 * @version 1.0.0
 *
 * @since 1.0.0
 */
 @Component
public class SpiriterMysqlConfiguration implements ConfigurationCustomizer {
    @Override
    public void customize(Configuration configuration) {
        configuration.addMapper(CommonMapper.class);
        configuration.addMapper(ColumnsMapper.class);
        configuration.addMapper(TablesMapper.class);
    }
}


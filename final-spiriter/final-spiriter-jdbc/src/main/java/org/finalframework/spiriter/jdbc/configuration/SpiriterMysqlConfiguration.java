package org.finalframework.spiriter.jdbc.configuration;


import org.apache.ibatis.session.Configuration;
import org.finalframework.spiriter.jdbc.dao.mapper.ColumnsMapper;
import org.finalframework.spiriter.jdbc.dao.mapper.CommonMapper;
import org.finalframework.spiriter.jdbc.dao.mapper.TablesMapper;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;

/**
 * @author likly
 * @version 1.0
 * @date 2020-05-20 20:41:36
 * @since 1.0
 */
@SpringComponent
public class SpiriterMysqlConfiguration implements ConfigurationCustomizer {
    @Override
    public void customize(Configuration configuration) {
        configuration.addMapper(CommonMapper.class);
        configuration.addMapper(ColumnsMapper.class);
        configuration.addMapper(TablesMapper.class);
    }
}


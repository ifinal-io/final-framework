package org.finalframework.spiriter.mysql.configuration;


import org.apache.ibatis.session.Configuration;
import org.finalframework.spiriter.mysql.dao.mapper.ColumnsMapper;
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
        configuration.addMapper(ColumnsMapper.class);
    }
}


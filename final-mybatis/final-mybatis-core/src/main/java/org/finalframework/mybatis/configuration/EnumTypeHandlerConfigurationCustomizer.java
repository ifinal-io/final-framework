package org.finalframework.mybatis.configuration;


import org.apache.ibatis.session.Configuration;
import org.finalframework.mybatis.handler.EnumTypeHandler;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-29 16:11:22
 * @see org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration
 * @since 1.0
 */
@SpringComponent
@SuppressWarnings("unused")
public class EnumTypeHandlerConfigurationCustomizer implements ConfigurationCustomizer {
    @Override
    public void customize(Configuration configuration) {
        configuration.getTypeHandlerRegistry().setDefaultEnumTypeHandler(EnumTypeHandler.class);
    }
}


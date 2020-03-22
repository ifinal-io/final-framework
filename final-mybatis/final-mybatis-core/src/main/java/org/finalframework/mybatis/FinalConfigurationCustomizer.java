package org.finalframework.mybatis;


import org.apache.ibatis.session.Configuration;
import org.finalframework.mybatis.handler.EnumTypeHandler;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;

/**
 * @author likly
 * @version 1.0
 * @date 2020-03-22 18:49:52
 * @since 1.0
 */
@SpringComponent
public class FinalConfigurationCustomizer implements ConfigurationCustomizer {
    @Override
    public void customize(Configuration configuration) {

        configuration.getTypeHandlerRegistry().setDefaultEnumTypeHandler(EnumTypeHandler.class);
    }
}


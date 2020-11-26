package org.ifinal.finalframework.mybatis.configuration;


import org.apache.ibatis.session.Configuration;
import org.ifinal.finalframework.mybatis.handler.EnumTypeHandler;
import org.ifinal.finalframework.mybatis.lang.FinalXMLLanguageDriver;
import org.ifinal.finalframework.mybatis.mapper.AbsMapper;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author likly
 * @version 1.0.0
 * @see org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration
 * @since 1.0.0
 */
@Component
@SuppressWarnings("unused")
public class EnumTypeHandlerConfigurationCustomizer implements ConfigurationCustomizer {
    private static final Logger logger = LoggerFactory.getLogger(EnumTypeHandlerConfigurationCustomizer.class);

    @Override
    public void customize(Configuration configuration) {
        logger.info("setDefaultEnumTypeHandler:{}", EnumTypeHandler.class.getCanonicalName());
        configuration.addMapper(AbsMapper.class);
        configuration.getTypeHandlerRegistry().setDefaultEnumTypeHandler(EnumTypeHandler.class);
        configuration.getLanguageRegistry().setDefaultDriverClass(FinalXMLLanguageDriver.class);
    }
}


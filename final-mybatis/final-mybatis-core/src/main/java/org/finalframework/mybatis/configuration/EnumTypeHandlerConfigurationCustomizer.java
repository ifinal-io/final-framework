package org.finalframework.mybatis.configuration;


import org.apache.ibatis.session.Configuration;
import org.finalframework.mybatis.handler.EnumTypeHandler;
import org.finalframework.mybatis.lang.FinalXMLLanguageDriver;
import org.finalframework.spring.annotation.factory.SpringComponent;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(EnumTypeHandlerConfigurationCustomizer.class);

    @Override
    public void customize(Configuration configuration) {
        logger.info("setDefaultEnumTypeHandler:{}", EnumTypeHandler.class.getCanonicalName());
        configuration.getTypeHandlerRegistry().setDefaultEnumTypeHandler(EnumTypeHandler.class);
//        configuration.getTypeAliasRegistry().registerAlias("XML",FinalXMLLanguageDriver.class);
//        configuration.getTypeAliasRegistry().registerAlias("RAW",FinalXMLLanguageDriver.class);
        configuration.getLanguageRegistry().setDefaultDriverClass(FinalXMLLanguageDriver.class);
    }
}


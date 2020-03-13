package org.finalframework.mybatis.autoconfigure;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.finalframework.core.Assert;
import org.finalframework.mybatis.handler.EnumTypeHandler;
import org.finalframework.mybatis.handler.LocalDateTimeTypeHandler;
import org.finalframework.mybatis.inteceptor.PageHelperPageableInterceptor;
import org.finalframework.mybatis.inteceptor.PageableInterceptor;
import org.finalframework.spring.annotation.factory.SpringConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-15 21:59:36
 * @since 1.0
 */
@SpringConfiguration
@Configuration
public class MybatisAutoConfiguration implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, SqlSessionFactory> beansOfType = applicationContext.getBeansOfType(SqlSessionFactory.class);
        if (Assert.isEmpty(beansOfType)) return;
        PageableInterceptor pageableInterceptor = new PageHelperPageableInterceptor();
        for (SqlSessionFactory sessionFactory : beansOfType.values()) {
            sessionFactory.getConfiguration().addInterceptor(pageableInterceptor);
            final TypeHandlerRegistry registry = sessionFactory.getConfiguration().getTypeHandlerRegistry();
            registry.setDefaultEnumTypeHandler(EnumTypeHandler.class);
            registry.register(LocalDateTime.class, new LocalDateTimeTypeHandler());
        }
    }
}

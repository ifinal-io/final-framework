package org.ifinalframework.data.mybatis;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.core.annotation.AnnotationUtils;

import org.ifinalframework.data.mybatis.configuration.FinalMybatisConfigurationCustomizer;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;

import javax.sql.DataSource;

import java.util.Arrays;
import java.util.Objects;

import lombok.Setter;

/**
 * MyBatisDataSourceAutoConfiguration.
 *
 * @author iimik
 * @version 1.0.0
 * @see org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration
 * @since 1.0.0
 */
public class MyBatisDataSourceConfigurationSupport implements BeanFactoryAware {

    @Setter
    private BeanFactory beanFactory;

    protected SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setPlugins(beanFactory.getBeanProvider(Interceptor.class).stream().toArray(Interceptor[]::new));
        bean.setTypeHandlers(beanFactory.getBeanProvider(TypeHandler.class).stream().toArray(TypeHandler[]::new));
        bean.setScriptingLanguageDrivers(
                beanFactory.getBeanProvider(LanguageDriver.class).stream().toArray(LanguageDriver[]::new));
        Configuration configuration = new Configuration();
        applyConfiguration(configuration);
        bean.setConfiguration(configuration);
        return bean.getObject();
    }

    protected SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    protected void applyConfiguration(Configuration configuration) {
        beanFactory.getBeanProvider(ConfigurationCustomizer.class).forEach(it -> it.customize(configuration));

        Class<?> targetClass = AopUtils.getTargetClass(this);

        if (Enhancer.isEnhanced(targetClass)) {
            targetClass = targetClass.getSuperclass();
        }

        MapperScan mapperScan = AnnotationUtils.getAnnotation(targetClass, MapperScan.class);
        if (Objects.nonNull(mapperScan)) {
            FinalMybatisConfigurationCustomizer customizer = new FinalMybatisConfigurationCustomizer();
            customizer.setPackages(Arrays.asList(mapperScan.basePackages()));
            customizer.customize(configuration);
        }

    }

}

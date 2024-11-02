package org.ifinalframework.boot.env;

import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.CollectionUtils;

import org.junit.jupiter.api.Test;

import java.util.List;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PropertySourceLoadersTest.
 *
 * @author iimik
 * @version 1.3.1
 * @since 1.3.1
 */
@Slf4j
class PropertySourceLoadersTest {

    @Test
    void loaders() {
        List<PropertySourceLoader> propertySourceLoaders = SpringFactoriesLoader.loadFactories(PropertySourceLoader.class,
                getClass().getClassLoader());
        for (PropertySourceLoader propertySourceLoader : propertySourceLoaders) {
            logger.info(propertySourceLoader.getClass().getName());
        }
        assertEquals(PropertiesPropertySourceLoader.class, propertySourceLoaders.get(0).getClass());
    }


    @Test
    @SneakyThrows
    void load() {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PropertySourceLoadersTest.class);
        PropertySourceLoaders loaders = new PropertySourceLoaders(context);
        List<PropertySource<?>> sources = loaders.load("classpath:application-ha*");
        for (PropertySource<?> source : sources) {
            context.getEnvironment().getPropertySources().addLast(source);
        }
        assertFalse(CollectionUtils.isEmpty(sources));
        assertEquals("SELECT 1", context.getEnvironment().getProperty("spring.datasource.druid.validation-query"));

    }
}
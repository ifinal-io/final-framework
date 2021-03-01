package org.ifinal.finalframework.core.io.support;

import org.springframework.context.ApplicationListener;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * SpringFactoriesLoaderTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j(topic = "springFactory")
class SpringFactoriesLoaderTest {

    @Test
    void loadApplicationListenerFactoryNames(){
        List<String> factoryNames = SpringFactoriesLoader.loadFactoryNames(ApplicationListener.class,null);
        factoryNames.forEach(it -> logger.info("{}",it));
    }

    @Test
    void loadApplicationListenerFactories(){
        List<ApplicationListener> listeners = SpringFactoriesLoader.loadFactories(ApplicationListener.class, null);
        listeners.forEach(it -> logger.info("{}",it.getClass()));
    }
}

package org.springframework.boot.env;

import org.springframework.boot.DefaultBootstrapContext;
import org.springframework.boot.logging.DeferredLogs;
import org.springframework.core.io.support.SpringFactoriesLoader;

import org.junit.jupiter.api.Test;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhimin.guo
 * @version 1.0.0
 **/
@Slf4j
class EnvironmentPostProcessorTest {
    @Test
    void test() {
        List<String> classNames = SpringFactoriesLoader.loadFactoryNames(EnvironmentPostProcessor.class, getClass().getClassLoader());
        EnvironmentPostProcessorsFactory factory = new ReflectionEnvironmentPostProcessorsFactory(getClass().getClassLoader(), classNames);
        List<EnvironmentPostProcessor> processors = factory.getEnvironmentPostProcessors(new DeferredLogs(), new DefaultBootstrapContext());
        for (EnvironmentPostProcessor processor : processors) {
            logger.info("{}", processor.getClass().getSimpleName());
        }
    }
}

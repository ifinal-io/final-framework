package org.finalframework.context;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author likly
 * @version 1.0
 * @date 2020/10/30 20:48:52
 * @since 1.0
 */

@Slf4j
public class ApplicationContextTest {

    @PostConstruct
    public void init() {
        logger.info("{}#init()", getClass().getSimpleName());
    }

    @PreDestroy
    public void destroy() {
        logger.info("{}#destory()", getClass().getSimpleName());
    }

    @Test
    public void test() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationContextTest.class);
        ApplicationContextTest bean = applicationContext.getBean(ApplicationContextTest.class);
        System.out.println(bean);
    }
}

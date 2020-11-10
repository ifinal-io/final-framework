package org.finalframework.context.initializer;

import lombok.extern.slf4j.Slf4j;
import org.finalframework.FinalFramework;
import org.finalframework.auto.spring.factory.annotation.SpringFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/10 14:07:41
 * @since 1.0
 */
@Slf4j
@SpringFactory(ApplicationContextInitializer.class)
public class FinalFrameworkApplicationContextInitializer extends AbsFrameworkApplicationContextInitializer<ConfigurableApplicationContext> {

    public FinalFrameworkApplicationContextInitializer() {
        super(FinalFramework.class);
    }

}

package org.ifinal.finalframework.context.initializer;

import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.FinalFramework;
import org.ifinal.finalframework.auto.spring.factory.annotation.SpringFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Registration a {@link FinalFramework} class into {@link ConfigurableApplicationContext}.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@SpringFactory(ApplicationContextInitializer.class)
@SuppressWarnings("unused")
public final class FinalFrameworkApplicationContextInitializer extends
    AbsFrameworkApplicationContextInitializer<ConfigurableApplicationContext> {

    public FinalFrameworkApplicationContextInitializer() {
        super(FinalFramework.class);
    }

}

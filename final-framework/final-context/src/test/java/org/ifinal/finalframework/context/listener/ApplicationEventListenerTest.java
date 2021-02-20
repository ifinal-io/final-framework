package org.ifinal.finalframework.context.listener;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import org.ifinal.finalframework.ContextApplicationContext;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * ApplicationContextInitializedEventListenerTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class ApplicationEventListenerTest {

    @Test
    void onApplicationEvent() {
        assertDoesNotThrow(() -> {
            SpringApplication application = new SpringApplication(ContextApplicationContext.class);

            application.addListeners(
                new ApplicationContextInitializedEventListener(),
                new ApplicationEnvironmentPreparedEventListener(),
                new ApplicationFailedEventListener(),
                new ApplicationPreparedEventListener(),
                new ApplicationReadyEventListener(),
                new ApplicationStartedEventListener(),
                new ApplicationStartingEventListener()
            );


            ConfigurableApplicationContext context = application.run();
        });
    }

}

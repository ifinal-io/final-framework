package org.ifinal.finalframework.context.listener;

import lombok.extern.slf4j.Slf4j;
import org.ifinal.auto.spring.factory.annotation.SpringApplicationListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@SpringApplicationListener
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        logger.info("Application Ready!!!");
    }

}



package org.finalframework.context.listener;

import lombok.extern.slf4j.Slf4j;
import org.finalframework.auto.spring.factory.annotation.SpringApplicationListener;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-09 21:18:06
 * @since 1.0
 */
@Slf4j
@SpringApplicationListener
public class ApplicationStartingEventListener implements ApplicationListener<ApplicationStartingEvent> {


    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        logger.info(event.getClass().getCanonicalName());
    }
}

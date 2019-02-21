package com.ilikly.finalframework.spring.web.listener;

import com.ilikly.finalframework.spring.coding.ApplicationEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author likly
 * @version 1.0
 * @date 2019-01-09 21:18:06
 * @since 1.0
 */
@Slf4j
@ApplicationEventListener
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        logger.info("Application Ready!!!");
    }
}

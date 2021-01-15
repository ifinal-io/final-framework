package org.ifinal.finalframework.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@SpringBootApplication
public class FinalApplication extends SpringBootServletInitializer {

    public static void main(final String[] args) {

        SpringApplication application = new SpringApplication(FinalApplication.class);

        application.addListeners(new ApplicationListener<ApplicationReadyEvent>() {
            @Override
            public void onApplicationEvent(final ApplicationReadyEvent event) {
                logger.info("onReady from ApplicationListener");
            }
        });

        application.run(args);

    }

    @EventListener
    public void onReady(final ApplicationReadyEvent readyEvent) {
        logger.info("onReady from @EventListener");
    }

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
        return builder.sources(FinalApplication.class);
    }

}

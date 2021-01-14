package org.ifinal.finalframework.example;

import org.ifinal.finalframework.context.listener.ApplicationReadyEventListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringBootApplication
public class FinalApplication extends SpringBootServletInitializer {

    public static void main(final String[] args) {

        SpringApplication application = new SpringApplication(FinalApplication.class);

        application.addListeners(new ApplicationReadyEventListener());

        application.run(args);

    }

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
        return builder.sources(FinalApplication.class);
    }

}

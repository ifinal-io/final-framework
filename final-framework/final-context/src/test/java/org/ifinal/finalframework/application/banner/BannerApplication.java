package org.ifinal.finalframework.application.banner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * BannerApplication.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@SpringBootApplication
public class BannerApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(BannerApplication.class);
//        application.setBannerMode(Mode.CONSOLE);
        application.run(args);
    }

}

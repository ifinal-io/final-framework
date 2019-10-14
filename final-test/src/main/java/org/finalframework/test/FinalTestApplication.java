package org.finalframework.test;

import org.finalframework.data.mapping.converter.NameConverterRegistry;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:13
 * @since 1.0
 */
@SpringBootApplication()
public class FinalTestApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {

        System.out.println(NameConverterRegistry.getInstance().getColumnNameConverter().getClass());


//        final long start = System.currentTimeMillis();
//        SpringApplication.run(FinalTestApplication.class, args);
//        final long end = System.currentTimeMillis();
//        System.out.println(end - start);
    }
}

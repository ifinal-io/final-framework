package com.ilikly.finalframework.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 22:13
 * @since 1.0
 */
@SpringBootApplication()
@EnableAutoConfiguration
public class FinalTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinalTestApplication.class, args);
    }
}

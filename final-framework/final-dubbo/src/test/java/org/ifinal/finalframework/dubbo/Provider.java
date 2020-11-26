package org.ifinal.finalframework.dubbo;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class Provider {


    public static void main(String[] args) throws IOException {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring-dubbo-provider.xml");
        context.start();
        System.in.read();
    }
}


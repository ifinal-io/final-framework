package org.ifinal.finalframework.dubbo;


import org.ifinal.finalframework.dubbo.service.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class Consumer {
    public static void main(String[] args) throws IOException {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring-dubbo-consumer.xml");
        context.start();

        final HelloService helloService = (HelloService) context.getBean("helloService");

        System.out.println(helloService.hello("dubbo"));

        System.in.read();
    }
}


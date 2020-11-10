package org.finalframework.dubbo;


import org.finalframework.dubbo.service.HelloService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author likly
 * @version 1.0
 * @date 2020-08-27 10:46:58
 * @since 1.0
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


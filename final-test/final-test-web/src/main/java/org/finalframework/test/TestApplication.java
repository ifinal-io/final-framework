package org.finalframework.test;


import org.finalframework.data.query.QEntity;
import org.finalframework.test.entity.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-05 13:59:11
 * @since 1.0
 */
@SpringBootApplication
public class TestApplication {


    public static void main(String[] args) {
        final QEntity<?, ?> properties = QEntity.from(Person.class);
        SpringApplication.run(TestApplication.class);
    }
}


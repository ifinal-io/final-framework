package org.finalframework.test;


import org.apache.ibatis.session.Configuration;
import org.finalframework.data.mapping.Entity;
import org.finalframework.mybatis.resumtmap.ResultMapHolder;
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
        final ResultMapHolder resultMapHolder = new ResultMapHolder(new Configuration(), Entity.from(Person.class));

        SpringApplication.run(TestApplication.class);
    }
}


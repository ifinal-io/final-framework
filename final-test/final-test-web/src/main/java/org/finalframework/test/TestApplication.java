package org.finalframework.test;


import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.Configuration;
import org.finalframework.data.mapping.Entity;
import org.finalframework.data.query.QEntity;
import org.finalframework.json.Json;
import org.finalframework.mybatis.resumtmap.ResultMapFactory;
import org.finalframework.test.dao.query.QPerson;
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
        final QEntity<?, ?> entity = QEntity.from(Person.class);
        final QPerson person = QPerson.Person;
        final ResultMap resultMap = ResultMapFactory.from(new Configuration(), Person.class);

        final Entity<Person> properties = Entity.from(Person.class);
        System.out.println(Json.toJson(properties));

//        SpringApplication.run(TestApplication.class);
    }
}


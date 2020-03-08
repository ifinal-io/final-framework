package org.finalframework.test;


import org.finalframework.data.query.Query;
import org.finalframework.json.Json;
import org.finalframework.test.dao.query.QPerson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author likly
 * @version 1.0
 * @date 2019-11-05 13:59:11
 * @since 1.0
 */
@SpringBootApplication(exclude = {
//        DataSourceAutoConfiguration.class
})
public class TestApplication {

    public static void main(String[] args) {

        Query query = new Query();
        query.page(1, 20);

        query.where(QPerson.age.eq(10));

        query.sort(QPerson.age.desc(), QPerson.id.asc());
        query.group(QPerson.name, QPerson.age);
        query.limit(200, 20);

        System.out.println(Json.toJson(query));


//        SpringApplication.run(TestApplication.class);
    }
}


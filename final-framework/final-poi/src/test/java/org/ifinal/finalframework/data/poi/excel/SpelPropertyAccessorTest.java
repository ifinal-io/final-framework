package org.ifinal.finalframework.data.poi.excel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * SpelPropertyAccessorTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class SpelPropertyAccessorTest {

    @Data
    @AllArgsConstructor
    private static class Person {

        private String name;

        private Integer age;

        private Date date;

    }

    @Test
    void read() {

        SpelPropertyAccessor accessor = new SpelPropertyAccessor();

        Person person = new Person("xiaoming", 12, new Date());

        assertEquals(person.getName(), accessor.read("name", person));
    }

}

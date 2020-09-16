package org.finalframework.data.mapping;

import org.finalframework.annotation.data.NonCompare;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-13 20:40:54
 * @since 1.0
 */
public class EntityTest {

    @Test
    public void testCompare() {
        Bean before = new Bean(1, "before");
        Bean after = new Bean(1, "after");
        List<CompareProperty> compare = Entity.compare(before, after);
        assertEquals(1, compare.size());
        compare.forEach(System.out::println);
    }

    public static class Bean {
        @NonCompare
        private Integer code;
        private String name;

        public Bean(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
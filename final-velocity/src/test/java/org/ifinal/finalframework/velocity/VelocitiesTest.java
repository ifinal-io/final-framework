package org.ifinal.finalframework.velocity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

/**
 * VelocitiesTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class VelocitiesTest {

    @Test
    void getValue() {
        Map<String, Object> context = new HashMap<>();
        context.put("name", "xiaoMing");
        context.put("age", 12);
        assertEquals("xiaoMing", Velocities.getValue("${name}", context));
        assertEquals("12", Velocities.getValue("${age}", context));
    }

    @Test
    void getValueFromObject() {
        Params params = new Params("xiaoMing", 12);
        assertEquals("xiaoMing", Velocities.getValue("${name}", params));
        assertEquals("12", Velocities.getValue("${age}", params));
    }

    @Data
    @AllArgsConstructor
    private static class Params {

        private String name;

        private Integer age;

    }

}

package org.ifinal.finalframework.velocity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

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

}

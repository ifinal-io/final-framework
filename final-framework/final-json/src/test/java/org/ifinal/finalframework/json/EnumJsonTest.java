package org.ifinal.finalframework.json;

import org.ifinal.finalframework.annotation.data.YN;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class EnumJsonTest {
    @Test
    void enum2Json() {
        assertEquals(YN.YES.getCode().toString(), Json.toJson(YN.YES));
        System.out.println(Json.toJson(YN.class));
    }
}

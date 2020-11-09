package org.finalframework.json;

import org.finalframework.annotation.data.YN;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/9 20:55:52
 * @since 1.0
 */
class EnumJsonTest {
    @Test
    void enum2Json() {
        assertEquals(YN.YES.getCode().toString(), Json.toJson(YN.YES));
        System.out.println(Json.toJson(YN.class));
    }
}

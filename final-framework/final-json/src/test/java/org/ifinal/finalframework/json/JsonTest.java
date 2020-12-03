package org.ifinal.finalframework.json;

import org.ifinal.finalframework.annotation.data.AbsEntity;
import org.ifinal.finalframework.annotation.data.YN;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class JsonTest {
    @Test
    void enum2Json() {
        assertEquals(YN.YES.getCode().toString(), Json.toJson(YN.YES));
        System.out.println(Json.toJson(YN.class));
    }

    @Test
    void entityJson() {
        AbsEntity entity = new AbsEntity();
        entity.setId(1L);
        entity.setCreated(LocalDateTime.now());
        System.out.println(Json.toJson(entity));
        assertEquals(1L, entity.getId());
    }
}

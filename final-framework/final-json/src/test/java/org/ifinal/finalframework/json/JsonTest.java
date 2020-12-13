package org.ifinal.finalframework.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.annotation.data.AbsEntity;
import org.ifinal.finalframework.annotation.data.YN;
import org.junit.jupiter.api.Test;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class JsonTest {

    @Test
    void enum2Json() {
        logger.info(Json.toJson(YN.YES));
        assertEquals(YN.YES.getCode().toString(), Json.toJson(YN.YES));
        assertEquals(YN.YES, Json.toObject(YN.YES.getCode().toString(), YN.class));

    }

    @Test
    void entityJson() {
        AbsEntity entity = new AbsEntity();
        entity.setId(1L);
        entity.setCreated(LocalDateTime.now());
        assertEquals(1L, entity.getId());
    }

}

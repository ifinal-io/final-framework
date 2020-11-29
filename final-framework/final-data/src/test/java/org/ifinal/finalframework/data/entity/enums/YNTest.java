package org.ifinal.finalframework.data.entity.enums;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ifinal.finalframework.annotation.data.YN;
import org.ifinal.finalframework.json.Json;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class YNTest {

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void of() throws Throwable {
        assertEquals(YN.YES, objectMapper.readValue(YN.YES.getCode().toString(), YN.class));
    }

    @Test
    void value() throws Throwable {
        assertEquals(YN.YES.getCode().toString(), Json.toJson(YN.YES));

    }
}
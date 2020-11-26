package org.ifinal.finalframework.data.entity.enums;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ifinal.finalframework.annotation.data.YN;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class YNTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void of() throws Throwable {
        assertEquals(YN.YES, objectMapper.readValue(objectMapper.writeValueAsString(YN.YES), YN.class));
    }

    @Test
    public void value() throws Throwable {
        assertEquals(YN.YES.getCode().toString(), objectMapper.writeValueAsString(YN.YES));

    }
}
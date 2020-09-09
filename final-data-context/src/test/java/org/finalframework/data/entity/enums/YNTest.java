

package org.finalframework.data.entity.enums;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.finalframework.annotation.data.YN;
import org.junit.jupiter.api.Test;


/**
 * @author likly
 * @version 1.0
 * @date 2019-02-16 09:30:26
 * @since 1.0
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
package org.finalframework.json.jackson;

import org.finalframework.data.entity.enums.YN;
import org.finalframework.json.Json;
import org.finalframework.json.JsonRegistry;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * @author likly
 * @version 1.0
 * @date 2020-02-16 22:38:47
 * @since 1.0
 */
public class JacksonJsonServiceTest {

    @Before
    public void setup() {
        JsonRegistry.getInstance().register(new JacksonJsonService());
    }

    @Test
    public void toJson() {
        System.out.println(Json.toJson(YN.values()));
        JacksonBean bean = new JacksonBean();
        bean.setCreated(LocalDateTime.now());
        bean.setYn(YN.YES);
        System.out.println(Json.toJson(bean));
    }

    @Test
    public void testToJson() {
    }
}
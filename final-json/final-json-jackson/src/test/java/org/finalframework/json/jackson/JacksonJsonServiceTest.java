package org.finalframework.json.jackson;

import org.finalframework.json.Json;
import org.finalframework.json.JsonRegistry;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import static org.junit.Assert.assertEquals;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-14 17:26:42
 * @since 1.0
 */
public class JacksonJsonServiceTest {

    public int getMethodType(){
        return 2;
    }

    @Before
    public void setUp(){
        JsonRegistry.getInstance().register(new JacksonJsonService());
    }

    @Test
    public void toJson() throws Throwable {

        // assert int
        assertEquals("1", Json.toJson(1));
        // assert String
        assertEquals("hello", Json.toJson("hello"));

        final Method getMethodType = JacksonJsonServiceTest.class.getMethod("getMethodType");
        final Type genericReturnType = getMethodType.getGenericReturnType();
        assertEquals("2",Json.toJson(getMethodType.invoke(this)));
        assertEquals("null",Json.toJson(null));
        assertEquals("1", Json.toJson(1));
        final String json = "{\"name\":\"xiaoming\"}";
        assertEquals(json,Json.toJson(json));
    }

    @Test
    public void parse() throws Throwable {
        final int parse = Json.parse("1", int.class);
        assertEquals(1, parse);
    }


}
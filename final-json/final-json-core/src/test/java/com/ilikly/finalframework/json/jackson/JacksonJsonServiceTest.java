package com.ilikly.finalframework.json.jackson;

import com.ilikly.finalframework.json.Json;
import com.ilikly.finalframework.json.JsonRegistry;
import com.ilikly.finalframework.json.JsonService;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import static org.junit.Assert.*;

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

    }


}
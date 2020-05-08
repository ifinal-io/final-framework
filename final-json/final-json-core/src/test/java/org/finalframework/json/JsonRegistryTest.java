package org.finalframework.json;

import static org.mockito.Mockito.when;

import junit.framework.TestCase;
import org.finalframework.data.entity.enums.YN;
import org.finalframework.json.jackson.JacksonJsonService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author sli
 * @version 1.0
 * @date 2020-04-22 22:56:19
 * @since 1.0
 */
public class JsonRegistryTest extends TestCase {

    @Mock
    private JsonRegistry jsonRegistry;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetJsonService() {
        //given

        //when
        when(jsonRegistry.getJsonService()).thenReturn(new JacksonJsonService());
        //then
        JsonService jsonService = jsonRegistry.getJsonService();
        System.out.println(Json.toJson(YN.class));
        System.out.printf("");
//        assertEquals();
    }
}
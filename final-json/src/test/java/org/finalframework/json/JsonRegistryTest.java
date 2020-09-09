

package org.finalframework.json;

import junit.framework.TestCase;
import org.finalframework.annotation.data.YN;
import org.finalframework.json.jackson.JacksonJsonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

/**
 * @author sli
 * @version 1.0
 * @date 2020-04-22 22:56:19
 * @since 1.0
 */
 class JsonRegistryTest extends TestCase {

    @Mock
    private JsonRegistry jsonRegistry;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetJsonService() {
        //given

        //when
        when(jsonRegistry.getJsonService()).thenReturn(new JacksonJsonService());
        //then
        System.out.println(Json.toJson(YN.class));
        System.out.printf("");
    }
}
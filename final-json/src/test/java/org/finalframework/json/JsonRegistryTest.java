

package org.finalframework.json;

import junit.framework.TestCase;
import org.finalframework.annotation.IView;
import org.finalframework.annotation.data.AbsEntity;
import org.finalframework.annotation.data.YN;
import org.finalframework.json.jackson.view.JsonViewValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

/**
 * @author sli
 * @version 1.0
 * @date 2020-04-22 22:56:19
 * @since 1.0
 */
 class JsonRegistryTest extends TestCase {


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetJsonService() {
        System.out.println(Json.toJson(YN.class));


        AbsEntity entity = new AbsEntity();
        entity.setId(1L);
        entity.setVersion(1L);
        entity.setLastModified(LocalDateTime.now());
        JsonViewValue jsonViewValue = new JsonViewValue(entity, IView.class);
        System.out.println(Json.toJson(jsonViewValue));
    }
}
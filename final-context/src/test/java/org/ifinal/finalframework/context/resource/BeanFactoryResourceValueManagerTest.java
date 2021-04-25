package org.ifinal.finalframework.context.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import org.ifinal.finalframework.FinalFramework;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * BeanFactoryResourceValueManagerTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class BeanFactoryResourceValueManagerTest {

    @Test
    void resourceValue() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
            FinalFramework.class);

        ResourceValueManager manager = context.getBean(ResourceValueManager.class);

        ResourceValueEntity entity = context.getBean(ResourceValueEntity.class);

        Collection<ResourceValueHolder> holders = manager.getResourceValueHolders("config.name");

        assertEquals(1, holders.size());

        manager.setValue("config.name", "123456");
        assertEquals("123456", entity.getName());

        manager.setValue("config.stores", "[2,1]");
        assertTrue(entity.getStores().contains(1));

    }

}

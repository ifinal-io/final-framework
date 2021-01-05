package org.ifinal.finalframework.context.resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import org.junit.jupiter.api.Test;

/**
 * ResourceValueUtilsTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class ResourceValueUtilsTest {

    @Test
    void resourceValues() {
        ResourceValueEntity entity = new ResourceValueEntity();
        Collection<ResourceValueHolder> holders = ResourceValueUtils
            .findAllResourceValueHolders(entity, ResourceValueEntity.class);

        assertEquals(2, holders.size());

    }

}

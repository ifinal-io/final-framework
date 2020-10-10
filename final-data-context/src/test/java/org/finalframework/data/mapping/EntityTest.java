package org.finalframework.data.mapping;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-13 20:40:54
 * @since 1.0
 */
@Slf4j
class EntityTest {

    @Test
    void testPersonEntity() {
        Entity<Person> entity = Entity.from(Person.class);
        entity.stream().forEach(property -> {
            logger.info("property={},column={},order={}", property.getName(), property.getColumn(), property.getOrder());
        });
    }
}
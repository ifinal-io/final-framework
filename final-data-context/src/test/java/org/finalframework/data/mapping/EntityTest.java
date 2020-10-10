package org.finalframework.data.mapping;

import lombok.extern.slf4j.Slf4j;
import org.finalframework.data.mapping.converter.NameConverterRegistry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


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
        assertEquals(Person.class.getSimpleName(), entity.getSimpleName());
        assertEquals(NameConverterRegistry.getInstance().getTableNameConverter().convert(Person.class.getSimpleName()), entity.getTable());
        logger.info("entity={},table={}", entity.getSimpleName(), entity.getTable());
        entity.stream().forEach(property -> {
            logger.info("property={},column={},order={}", property.getName(), property.getColumn(), property.getOrder());
        });
    }
}
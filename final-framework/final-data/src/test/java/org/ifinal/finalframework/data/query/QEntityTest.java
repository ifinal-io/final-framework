package org.ifinal.finalframework.data.query;

import lombok.extern.slf4j.Slf4j;
import org.ifinal.finalframework.data.mapping.Person;
import org.junit.jupiter.api.Test;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class QEntityTest {

    @Test
    void testQEntity() {
        QEntity<?, ?> entity = QEntity.from(Person.class);
        logger.info("entity={},table={}", entity.getSimpleName(), entity.getTable());

        entity.forEach(property -> {
            logger.info("property={},column={},javaType={},typeHandler={}",
                    property.getName(), property.getColumn(),
                    property.getType(), property.getTypeHandler()
            );
        });


    }
}

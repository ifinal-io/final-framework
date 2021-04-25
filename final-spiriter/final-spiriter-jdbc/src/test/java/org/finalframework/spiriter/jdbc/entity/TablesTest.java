package org.ifinal.finalframework.spiriter.jdbc.entity;

import org.ifinal.finalframework.data.query.QEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class TablesTest {


    @Test
    public void test() {
        final QEntity<?, ?> entity = QEntity.from(Tables.class);

        assertEquals("TABLE_NAME", entity.getRequiredProperty("name").getColumn());
    }
}
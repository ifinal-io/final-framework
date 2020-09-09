

package org.finalframework.spiriter.jdbc.entity;

import org.finalframework.data.query.QEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author likly
 * @version 1.0
 * @date 2020-06-11 17:06:01
 * @since 1.0
 */
class TablesTest {


    @Test
    public void test() {
        final QEntity<?, ?> entity = QEntity.from(Tables.class);

        assertEquals("TABLE_NAME", entity.getRequiredProperty("name").getColumn());
    }
}
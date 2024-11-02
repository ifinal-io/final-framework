package org.ifinalframework.core;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ParamsBuilderTest.
 *
 * @author iimik
 * @version 1.3.1
 * @since 1.3.1
 */
class ParamsBuilderTest {

    @Test
    void test(){
        IUpdate update = new IUpdate() {
        };
        Map<String, Object> map = ParamsBuilder.builder()
                .table("table")
                .view(IView.class)
                .ignore(true)
                .list(Arrays.asList(1, 2))
                .update(1)
                .update(update)
                .selective(true)
//                .query(new PageQuery())
                .id(1L)
                .ids(Arrays.asList(1L,2L))
                .build();


        assertEquals("table",map.get(ParamsBuilder.TABLE_PARAM_NAME));
        assertEquals(IView.class,map.get(ParamsBuilder.VIEW_PARAM_NAME));
        assertEquals(true,map.get(ParamsBuilder.IGNORE_PARAM_NAME));
        assertEquals(Arrays.asList(1,2),map.get(ParamsBuilder.LIST_PARAM_NAME));
        assertEquals(1,map.get(ParamsBuilder.ENTITY_PARAM_NAME));
        assertEquals(update,map.get(ParamsBuilder.UPDATE_PARAM_NAME));
        assertEquals(true,map.get(ParamsBuilder.SELECTIVE_PARAM_NAME));
        assertEquals(1L,map.get(ParamsBuilder.ID_PARAM_NAME));
        assertEquals(Arrays.asList(1L,2L),map.get(ParamsBuilder.IDS_PARAM_NAME));

    }

}
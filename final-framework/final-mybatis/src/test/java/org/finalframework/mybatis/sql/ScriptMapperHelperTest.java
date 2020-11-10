package org.finalframework.mybatis.sql;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author likly
 * @version 1.0
 * @date 2020/10/12 14:09:56
 * @since 1.0
 */
@Slf4j
class ScriptMapperHelperTest {

    @Test
    void formatTest() {
        String test = ScriptMapperHelper.formatTest("item", "creator.id", false);
        logger.info(test);
        assertEquals("item.creator != null", test);
    }


    @Test
    void formatTest2() {
        String test = ScriptMapperHelper.formatTest("item", "creator.id", true);
        logger.info(test);
        assertEquals("item.creator != null and item.creator.id != null", test);
    }
}
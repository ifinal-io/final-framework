package org.ifinal.finalframework.annotation.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * IEnumTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class IEnumTest {

    @Getter
    @AllArgsConstructor
    enum YN implements IEnum<Integer> {
        YES(1, "YES"), NO(0, "NO");

        private final Integer code;

        private final String desc;
    }

    @Test
    void valueOf() {

        Assertions.assertEquals(YN.YES, IEnum.valueOf(YN.class, 1));
        Assertions.assertEquals(YN.YES, IEnum.valueOf(YN.class, Integer.valueOf("1")));
        Assertions.assertNull(IEnum.valueOf(YN.class, -1));
    }

}
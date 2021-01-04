package org.ifinal.finalframework.util;

import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * EnumsTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class EnumsTest {

    @AllArgsConstructor
    @Getter
    public enum Yn {
        YES(1), NO(0);

        private final Integer value;

        public static Yn valueOf(Integer code) {
            for (final Yn constant : Yn.class.getEnumConstants()) {
                if (Asserts.isEquals(constant.getValue(), code)) {
                    return constant;
                }
            }

            return null;
        }

    }

    @Test
    void findEnum() {
        Assertions.assertEquals(Yn.YES, Enums.findEnum(Yn.class, "valueOf", Integer.class, 1));
    }

    @Test
    void getEnumI18NCode() {

        Assertions.assertEquals(String.join(".", Yn.class.getCanonicalName(), Yn.YES.name().toLowerCase(Locale.ROOT)),
            Enums.getEnumI18NCode(Yn.YES));

    }

}
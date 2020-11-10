package org.finalframework.annotation;

import java.util.Locale;

/**
 * @author likly
 * @version 1.0
 * @date 2020/9/15 14:08:48
 * @since 1.0
 */
public final class I18N {

    private I18N() {
    }

    public static String enumCode(Enum<?> value) {
        return value.getClass().getCanonicalName() + "." + value.name().toLowerCase(Locale.ENGLISH);
    }

}

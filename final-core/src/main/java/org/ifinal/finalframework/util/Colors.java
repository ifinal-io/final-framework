package org.ifinal.finalframework.util;

import java.awt.Color;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Colors.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Colors {

    private static final Map<String, Color> CACHE = new HashMap<>(64);

    static {
        CACHE.put("RED", Color.RED);
    }

    public Colors() {
    }

    public static Color decode(String color){
        if (color.startsWith("#")) {
            return Color.decode(color.replace("#", "0x"));
        }

        if (color.startsWith("0x") || color.startsWith("0X")) {
            return Color.decode(color);
        }

        return CACHE.get(color.toUpperCase(Locale.ROOT));
    }

}

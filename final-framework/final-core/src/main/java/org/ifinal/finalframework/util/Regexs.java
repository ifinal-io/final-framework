package org.ifinal.finalframework.util;

import java.util.regex.Pattern;

/**
 * Regexs.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Regexs {

    private Regexs() {
    }

    public static boolean matches(final Pattern pattern, final CharSequence input) {
        return pattern.matcher(input).matches();
    }

}

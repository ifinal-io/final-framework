package org.ifinal.finalframework.core.generator;

import org.springframework.lang.NonNull;

import static java.util.Locale.ENGLISH;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public interface NameGenerator {
    static String capitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        return name.substring(0, 1).toUpperCase(ENGLISH) + name.substring(1);
    }

    static String capitalize(@NonNull String prefix, String name) {
        return prefix + capitalize(name);
    }

    static String decapitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }

        int pos = 0;
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) >= 'A' && name.charAt(i) <= 'Z') {
                pos++;
            } else {
                break;
            }
        }

        return name.substring(0, pos).toLowerCase(ENGLISH) + name.substring(pos);
    }

    static String decapitalize(String name, String prefix) {
        return decapitalize(name.substring(prefix.length()));
    }
}

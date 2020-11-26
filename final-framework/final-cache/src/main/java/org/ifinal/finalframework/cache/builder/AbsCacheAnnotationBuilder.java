package org.ifinal.finalframework.cache.builder;


import org.ifinal.finalframework.util.Asserts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class AbsCacheAnnotationBuilder {
    private static final String DELIMITER = ":";

    protected static Collection<String> parse(String[] keyOrField, String delimiter) {
        if (Asserts.isEmpty(keyOrField)) {
            return null;
        }
        final String split = getDelimiter(delimiter);
        List<String> list = new ArrayList<>();
        Arrays.stream(keyOrField)
                .map(item -> item.split(split))
                .forEach(items -> list.addAll(Arrays.asList(items)));
        return list;
    }

    protected static String getDelimiter(String delimiter) {
        return Asserts.isBlank(delimiter) ? DELIMITER : delimiter.trim();
    }


}

package org.finalframework.cache.builder;


import org.finalframework.core.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2019-03-11 09:44:18
 * @since 1.0
 */
public class AbsCacheAnnotationBuilder {


    protected static Collection<String> parse(String[] keyOrField, String delimiter) {
        if (Assert.isEmpty(keyOrField)) {
            return null;
        }

        List<String> list = new ArrayList<>();
        Arrays.stream(keyOrField)
                .map(item -> item.split(delimiter))
                .forEach(items -> list.addAll(Arrays.asList(items)));
        return list;
    }


}

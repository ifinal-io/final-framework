package org.ifinal.finalframework.cache;

import org.ifinal.finalframework.cache.annotation.CacheDel;
import org.ifinal.finalframework.cache.annotation.CacheIncrement;
import org.ifinal.finalframework.cache.annotation.CacheLock;
import org.ifinal.finalframework.cache.annotation.CacheValue;
import org.ifinal.finalframework.cache.annotation.Cacheable;
import org.ifinal.finalframework.core.annotation.AnnotationAttributesProcessor;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class CacheAnnotationAttributeProcessor implements AnnotationAttributesProcessor {

    private static final String DELIMITER = ":";

    /**
     * @see Cacheable#key()
     * @see CacheIncrement#key()
     * @see CacheDel#key()
     * @see CacheLock#key()
     * @see CacheValue#key()
     */
    private static final String ATTRIBUTE_NAME_KEY = "key";
    /**
     * @see Cacheable#field()
     * @see CacheIncrement#field()
     * @see CacheValue#field()
     */
    private static final String ATTRIBUTE_NAME_FIELD = "field";
    private static final String ATTRIBUTE_NAME_DELIMITER = "delimiter";


    private static Collection<String> parse(final String[] keyOrField, final String delimiter) {

        if (Asserts.isEmpty(keyOrField)) {
            return Collections.emptyList();
        }
        final String split = getDelimiter(delimiter);
        List<String> list = new ArrayList<>();
        Arrays.stream(keyOrField)
                .map(item -> item.split(split))
                .forEach(items -> list.addAll(Arrays.asList(items)));
        return list;
    }

    private static String getDelimiter(final String delimiter) {

        return Asserts.isBlank(delimiter) ? DELIMITER : delimiter.trim();
    }


    @Override
    public void doProcess(final @NonNull AnnotatedElement annotatedElement, final @NonNull AnnotationAttributes annotationAttributes) {

        processKeyOrField(annotationAttributes, ATTRIBUTE_NAME_KEY);
        processKeyOrField(annotationAttributes, ATTRIBUTE_NAME_FIELD);
    }


    private void processKeyOrField(final AnnotationAttributes annotationAttributes, final String name) {

        if (annotationAttributes.containsKey(name)) {
            Collection<String> strings = parse(annotationAttributes.getStringArray(name), getDelimiter(annotationAttributes.getString(ATTRIBUTE_NAME_DELIMITER)));
            annotationAttributes.put(name, strings.toArray(new String[0]));
        }
    }
}

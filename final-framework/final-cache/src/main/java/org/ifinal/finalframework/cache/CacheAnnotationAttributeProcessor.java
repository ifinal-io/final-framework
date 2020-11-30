package org.ifinal.finalframework.cache;

import org.ifinal.finalframework.cache.annotation.*;
import org.ifinal.finalframework.core.annotation.AnnotationAttributesProcessor;
import org.ifinal.finalframework.util.Asserts;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.lang.NonNull;

import java.lang.reflect.AnnotatedElement;
import java.util.*;

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


    private static Collection<String> parse(String[] keyOrField, String delimiter) {
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

    private static String getDelimiter(String delimiter) {
        return Asserts.isBlank(delimiter) ? DELIMITER : delimiter.trim();
    }


    @Override
    public void doProcess(@NonNull AnnotatedElement annotatedElement, @NonNull AnnotationAttributes annotationAttributes) {
        processKeyOrField(annotationAttributes, ATTRIBUTE_NAME_KEY);
        processKeyOrField(annotationAttributes, ATTRIBUTE_NAME_FIELD);
    }


    private void processKeyOrField(AnnotationAttributes annotationAttributes, String name) {
        if (annotationAttributes.containsKey(name)) {
            annotationAttributes.put(name, parse(annotationAttributes.getStringArray(name), getDelimiter(annotationAttributes.getString(ATTRIBUTE_NAME_DELIMITER))));
        }
    }
}

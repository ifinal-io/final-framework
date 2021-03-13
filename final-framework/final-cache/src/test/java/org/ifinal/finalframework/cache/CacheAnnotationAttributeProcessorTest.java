package org.ifinal.finalframework.cache;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;

import org.ifinal.finalframework.annotation.cache.Cacheable;
import org.ifinal.finalframework.util.Reflections;

import java.lang.reflect.Method;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class CacheAnnotationAttributeProcessorTest {

    private final CacheAnnotationAttributeProcessor processor = new CacheAnnotationAttributeProcessor();

    @Test
    void cacheable() {
        Method cacheable = Reflections.findRequiredMethod(CacheServiceImpl.class, "cacheable", Integer.class);

        Set<Cacheable> annotations = AnnotatedElementUtils.findAllMergedAnnotations(cacheable, Cacheable.class);

        Assertions.assertTrue(annotations.size() > 0);

        for (Cacheable annotation : annotations) {
            AnnotationAttributes annotationAttributes = Reflections.getAnnotationAttributes(annotation);
            processor.doProcess(cacheable, annotationAttributes);
            logger.info("find cacheable annotation attributes: {}", annotationAttributes);
        }

    }

}

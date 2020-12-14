package org.ifinal.finalframework.util;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.AnnotationAttributes;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
class ReflectionsTest {

    @Test
    void findAnnotationAttributes() {
        final Method method = Reflections.findRequiredMethod(ReflectionTarget.class, "method");
        Assertions.assertNotNull(method, "not found method of method");
        final AnnotationAttributes annotationAttributes = Reflections.findAnnotationAttributes(method, Cacheable.class);
        logger.info("find annotation attributes: {}", annotationAttributes);
        Assertions.assertNotNull(annotationAttributes);
    }

}

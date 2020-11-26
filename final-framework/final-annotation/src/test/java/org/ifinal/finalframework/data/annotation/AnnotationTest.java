package org.ifinal.finalframework.data.annotation;

import org.ifinal.finalframework.annotation.data.Table;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
public class AnnotationTest {


    public void testTableAnnotation() {
        final Table table = AnnotationUtils.findAnnotation(TestBean.class, Table.class);
    }
}

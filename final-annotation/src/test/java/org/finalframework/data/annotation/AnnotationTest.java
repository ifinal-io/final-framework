package org.finalframework.data.annotation;

import org.finalframework.annotation.data.Table;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author likly
 * @version 1.0
 * @date 2019-02-22 13:18:59
 * @since 1.0
 */
public class AnnotationTest {


    public void testTableAnnotation() {
        final Table table = AnnotationUtils.findAnnotation(TestBean.class, Table.class);
    }
}

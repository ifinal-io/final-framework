package org.springframework.context.annotation;

import org.springframework.core.type.AnnotationMetadata;

import org.ifinal.finalframework.FinalFramework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * ConfigurationClassUtilsTest.
 *
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
class ConfigurationClassUtilsTest {

    @Test
    void isConfigurationCandidate() {
        Assertions.assertTrue(ConfigurationClassUtils.isConfigurationCandidate(AnnotationMetadata.introspect(FinalFramework.class)));
    }



}

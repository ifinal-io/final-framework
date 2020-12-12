package org.ifinal.finalframework;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * final framework {@link ComponentScan} configuration.
 *
 * @author likly
 * @version 1.0.0
 * @see org.springframework.context.annotation.ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry(BeanDefinitionRegistry)
 * @since 1.0.0
 */
@ComponentScan
@ImportResource({
        FinalFramework.CLASS_PATH_SPRING_CONFIG_XML,
        FinalFramework.CLASS_PATH_CONFIG_SPRING_CONFIG_XML,
        FinalFramework.CLASS_PATH_SPRING_SPRING_CONFIG_XML
})
public class FinalFramework {

    static final String CLASS_PATH_SPRING_CONFIG_XML = "classpath:spring-config-*.xml";

    static final String CLASS_PATH_CONFIG_SPRING_CONFIG_XML = "classpath*:config/spring-config-*.xml";

    static final String CLASS_PATH_SPRING_SPRING_CONFIG_XML = "classpath*:spring/spring-config-*.xml";

}

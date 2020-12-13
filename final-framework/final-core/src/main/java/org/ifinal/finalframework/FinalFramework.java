package org.ifinal.finalframework;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.annotation.ImportResource;

/**
 * final framework {@link ComponentScan} configuration.
 *
 * @author likly
 * @version 1.0.0
 * @see ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry(BeanDefinitionRegistry)
 * @since 1.0.0
 */
@Slf4j
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

    @Getter
    private final String name;

    public FinalFramework() {
        // prevents calls from subclass
        logger.info("Final Framework loading...");
        this.name = "Final Framework";
    }

}

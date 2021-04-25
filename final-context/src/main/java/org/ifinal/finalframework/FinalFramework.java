package org.ifinal.finalframework;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * An {@link Configuration} class for {@code finalframework}.
 *
 * <h3>ComponentScan</h3>
 * <p>{@link FinalFramework} use default packages of {@code org.ifinal.finalframework} to scan {@link Component}s.</p>
 *
 * <h3>ImportResource</h3>
 * <p>Import the resources from:</p>
 * <ul>
 *     <li>{@code classpath:spring-config-*.xml}</li>
 *     <li>{@code classpath*:config/spring-config-*.xml}</li>
 *     <li>{@code classpath*:spring/spring-config-*.xml}</li>
 * </ul>
 *
 * @author likly
 * @version 1.0.0
 * @see ComponentScan
 * @see ImportResource
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
public class FinalFramework implements BeanNameAware {

    static final String CLASS_PATH_SPRING_CONFIG_XML = "classpath:spring-config-*.xml";

    static final String CLASS_PATH_CONFIG_SPRING_CONFIG_XML = "classpath*:config/spring-config-*.xml";

    static final String CLASS_PATH_SPRING_SPRING_CONFIG_XML = "classpath*:spring/spring-config-*.xml";

    @Getter
    @Setter
    private String beanName;

}

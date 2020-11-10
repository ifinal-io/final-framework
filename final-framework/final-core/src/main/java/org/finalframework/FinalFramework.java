package org.finalframework;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.type.classreading.MetadataReaderFactory;

/**
 * final framework {@link ComponentScan} configuration.
 *
 * @author likly
 * @version 1.0
 * @date 2020/11/10 15:51:24
 * @see org.springframework.context.annotation.ConfigurationClassPostProcessor#postProcessBeanDefinitionRegistry(BeanDefinitionRegistry)
 * @see org.springframework.context.annotation.ConfigurationClassUtils#checkConfigurationClassCandidate(BeanDefinition, MetadataReaderFactory)
 * @since 1.0
 */
@ComponentScan
public class FinalFramework {

}

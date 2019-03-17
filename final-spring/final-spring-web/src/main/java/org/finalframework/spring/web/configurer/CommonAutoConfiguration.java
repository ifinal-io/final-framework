package org.finalframework.spring.web.configurer;

import org.finalframework.spring.coding.AutoConfiguration;
import org.finalframework.spring.web.converter.DateConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author likly
 * @version 1.0
 * @date 2018-12-26 14:15:24
 * @since 1.0
 */
@Import({
        SpringResourceConfigurer.class,
        CorsConfigurer.class,
        SpringWebMvcConfigurer.class,
        DateConverter.class
})
@Configuration
@AutoConfiguration
public class CommonAutoConfiguration {

}

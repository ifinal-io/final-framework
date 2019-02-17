package com.ilikly.finalframework.spring.web.configurer;

import com.ilikly.finalframework.spring.coding.AutoConfiguration;
import com.ilikly.finalframework.spring.web.converter.String2DateConverter;
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
        String2DateConverter.class
})
@Configuration
@AutoConfiguration
public class CommonAutoConfiguration {

}

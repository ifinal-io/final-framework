package com.ilikly.finalframework.spring.web.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-29 16:31
 * @since 1.0
 */
@Configuration
@ImportResource({"classpath:spring-config-*.xml", "classpath*:config/spring-config-*.xml", "classpath*:spring/spring-config-*.xml"})
public class SpringResourceConfigurer {

}

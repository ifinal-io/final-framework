package com.ilikly.finalframework.spring.web.configurer;

import org.springframework.context.annotation.ImportResource;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-29 16:31
 * @since 1.0
 */
@ImportResource({"classpath*:spring-config-*.xml", "classpath*:config/spring-config-*.xml"})
public class SpringConfigResource {
}

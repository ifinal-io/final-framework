package org.finalframework.spring.web.configurer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-29 16:31
 * @since 1.0
 */
@Configuration
@ImportResource({"classpath:spring-config-*.xml", "classpath*:config/spring-config-*.xml", "classpath*:spring/spring-config-*.xml"})
public class SpringResourceConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(SpringResourceConfigurer.class);

    public SpringResourceConfigurer() {
        logger.info("加载资源文件目录：{}", String.join(",", AnnotationUtils.findAnnotation(SpringResourceConfigurer.class, ImportResource.class).value()));
    }
}

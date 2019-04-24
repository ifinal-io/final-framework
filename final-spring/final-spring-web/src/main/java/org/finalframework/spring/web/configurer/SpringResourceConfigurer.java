package org.finalframework.spring.web.configurer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * spring-config-*.xml 资源文件加载配置。
 *
 * @author likly
 * @version 1.0
 * @date 2018-09-29 16:31
 * @since 1.0
 */
@Configuration
@ImportResource({
        SpringResourceConfigurer.CLASS_PATH_SPRING_CONFIG_XML,
        SpringResourceConfigurer.CLASS_PATH_CONFIG_SPRING_CONFIG_XML,
        SpringResourceConfigurer.CLASS_PATH_SPRING_SPRING_CONFIG_XML
})
public class SpringResourceConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(SpringResourceConfigurer.class);

    static final String CLASS_PATH_SPRING_CONFIG_XML = "classpath:spring-config-*.xml";
    static final String CLASS_PATH_CONFIG_SPRING_CONFIG_XML = "classpath*:config/spring-config-*.xml";
    static final String CLASS_PATH_SPRING_SPRING_CONFIG_XML = "classpath*:spring/spring-config-*.xml";

    public SpringResourceConfigurer() {
        logger.info("加载资源文件目录：{}", String.join(",", AnnotationUtils.findAnnotation(SpringResourceConfigurer.class, ImportResource.class).value()));
    }
}

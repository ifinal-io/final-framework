package org.finalframework.dashboard.core.service.impl;

import lombok.Setter;
import org.finalframework.dashboard.core.service.BeanService;
import org.finalframework.dashboard.core.service.query.BeanQuery;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/17 20:23:14
 * @since 1.0
 */
@Service
class BeanServiceImpl implements BeanService, ApplicationContextAware {

    @Setter
    private ApplicationContext applicationContext;

    @Override
    public List<Class<?>> query(@NotNull BeanQuery query) {
        return Arrays.stream(applicationContext.getBeanDefinitionNames())
                .map(applicationContext::getBean)
                .map(AopUtils::getTargetClass)
                .filter(bean -> Objects.isNull(query.getAnnotation()) || AnnotatedElementUtils.isAnnotated(bean, query.getAnnotation()))
                .collect(Collectors.toList());
    }
}

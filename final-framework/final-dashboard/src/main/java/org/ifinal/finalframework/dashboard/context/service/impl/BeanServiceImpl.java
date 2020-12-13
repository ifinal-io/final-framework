package org.ifinal.finalframework.dashboard.context.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Setter;
import org.ifinal.finalframework.dashboard.context.entity.BeanDefinition;
import org.ifinal.finalframework.dashboard.context.service.BeanService;
import org.ifinal.finalframework.dashboard.context.service.query.BeanQuery;
import org.ifinal.finalframework.util.Proxies;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author likly
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
class BeanServiceImpl implements BeanService, ApplicationContextAware {

    private static final String SPRING_FRAMEWORK_PACKAGE = "org.springframework";

    private static final String FINAL_FRAMEWORK_PACKAGE = "org.ifinal.finalframework";

    @Setter
    private ApplicationContext applicationContext;

    @Override
    public List<BeanDefinition> query(final @NotNull BeanQuery query) {

        final List<BeanDefinition> beanDefinitions = new ArrayList<>();

        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            Class<?> targetClass = Proxies.targetClass(bean);

            if (!Boolean.TRUE.equals(query.getSpringFramework()) && targetClass.getPackage().getName()
                .startsWith(SPRING_FRAMEWORK_PACKAGE)) {
                continue;
            }

            if (!Boolean.TRUE.equals(query.getFinalFramework()) && targetClass.getPackage().getName()
                .startsWith(FINAL_FRAMEWORK_PACKAGE)) {
                continue;
            }

            final BeanDefinition beanDefinition = new BeanDefinition();
            beanDefinition.setName(beanDefinitionName);
            beanDefinition.setTargetClass(targetClass);
            beanDefinitions.add(beanDefinition);
        }

        return beanDefinitions;
    }

}

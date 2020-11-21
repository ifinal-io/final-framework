package org.finalframework.dashboard.context.service.impl;

import lombok.Setter;
import org.finalframework.dashboard.context.entity.BeanDefinition;
import org.finalframework.dashboard.context.service.BeanService;
import org.finalframework.dashboard.context.service.query.BeanQuery;
import org.finalframework.util.Proxies;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/17 20:23:14
 * @since 1.0
 */
@Service
class BeanServiceImpl implements BeanService, ApplicationContextAware {


    private static final String SPRING_FRAMEWORK_PACKAGE = "org.springframework";
    private static final String FINAL_FRAMEWORK_PACKAGE = "org.finalframework";


    @Setter
    private ApplicationContext applicationContext;

    @Override
    public List<BeanDefinition> query(@NotNull BeanQuery query) {

        final List<BeanDefinition> beanDefinitions = new ArrayList<>();


        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            Class<?> targetClass = Proxies.targetClass(bean);

            if (!Boolean.TRUE.equals(query.getSpringFramework()) && targetClass.getPackage().getName().startsWith(SPRING_FRAMEWORK_PACKAGE)) {
                continue;
            }

            if (!Boolean.TRUE.equals(query.getFinalFramework()) && targetClass.getPackage().getName().startsWith(FINAL_FRAMEWORK_PACKAGE)) {
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

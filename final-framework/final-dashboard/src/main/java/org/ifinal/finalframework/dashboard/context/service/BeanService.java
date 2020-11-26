package org.ifinal.finalframework.dashboard.context.service;

import org.ifinal.finalframework.dashboard.context.entity.BeanDefinition;
import org.ifinal.finalframework.dashboard.context.service.query.BeanQuery;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author likly
 * @version 1.0.0
 * @see org.springframework.context.ApplicationContext
 * @see org.springframework.beans.factory.BeanFactoryUtils
 * @since 1.0.0
 */
public interface BeanService {

    List<BeanDefinition> query(@NotNull BeanQuery query);

}

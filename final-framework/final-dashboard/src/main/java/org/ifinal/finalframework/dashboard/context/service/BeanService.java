package org.ifinal.finalframework.dashboard.context.service;

import java.util.List;
import javax.validation.constraints.NotNull;
import org.ifinal.finalframework.dashboard.context.entity.BeanDefinition;
import org.ifinal.finalframework.dashboard.context.service.query.BeanQuery;

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

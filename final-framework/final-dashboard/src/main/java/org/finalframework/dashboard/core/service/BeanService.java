package org.finalframework.dashboard.core.service;

import org.finalframework.dashboard.core.service.query.BeanQuery;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2020/11/17 20:15:05
 * @see org.springframework.context.ApplicationContext
 * @see org.springframework.beans.factory.BeanFactoryUtils
 * @since 1.0
 */
public interface BeanService {

    List<Class<?>> query(@NotNull BeanQuery query);

}

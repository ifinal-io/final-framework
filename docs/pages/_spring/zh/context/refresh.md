---
formatter: off
title: Refresh 
subtitle: refresh 
description: refresh 
tags: [] 
date: 2021-01-11 18:19:37 +800 
version: 1.0
formatter: on
---

# Refresh

## 简介

`ConfigurableApplicationContext` 定义了容器加载或刷新的接口 `refresh`。

> Load or refresh the persistent representation of the configuration, which might be from Java-based configuration, an XML file, a
> properties file, a relational database schema, or some other format.
>
> As this is a startup method, it should destroy already created singletons if it fails, to avoid dangling resources. In other words,
> after invocation of this method, either all or no singletons at all should be instantiated.

## 定义

```java
package org.springframework.context;

public interface ConfigurableApplicationContext extends ApplicationContext, Lifecycle, Closeable {

    void refresh() throws BeansException, IllegalStateException;

}
```

## Refresh

```java
package org.springframework.context.support;

public abstract class AbstractApplicationContext extends DefaultResourceLoader
    implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeansException, IllegalStateException {
        synchronized (this.startupShutdownMonitor) {
            // Prepare this context for refreshing.
            prepareRefresh();

            // Tell the subclass to refresh the internal bean factory.
            ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

            // Prepare the bean factory for use in this context.
            prepareBeanFactory(beanFactory);

            try {
                // Allows post-processing of the bean factory in context subclasses.
                postProcessBeanFactory(beanFactory);

                // Invoke factory processors registered as beans in the context.
                invokeBeanFactoryPostProcessors(beanFactory);

                // Register bean processors that intercept bean creation.
                registerBeanPostProcessors(beanFactory);

                // Initialize message source for this context.
                initMessageSource();

                // Initialize event multicaster for this context.
                initApplicationEventMulticaster();

                // Initialize other special beans in specific context subclasses.
                onRefresh();

                // Check for listener beans and register them.
                registerListeners();

                // Instantiate all remaining (non-lazy-init) singletons.
                // 实例化所有非懒加载的单例
                finishBeanFactoryInitialization(beanFactory);

                // Last step: publish corresponding event.
                finishRefresh();
            } catch (BeansException ex) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Exception encountered during context initialization - " +
                        "cancelling refresh attempt: " + ex);
                }

                // Destroy already created singletons to avoid dangling resources.
                destroyBeans();

                // Reset 'active' flag.
                cancelRefresh(ex);

                // Propagate exception to caller.
                throw ex;
            } finally {
                // Reset common introspection caches in Spring's core, since we
                // might not ever need metadata for singleton beans anymore...
                resetCommonCaches();
            }
        }
    }

}
```

![refresh](http://processon.com/chart_image/5fa274c963768943485bbf37.png)
---
formatter: off
layout: post
title: Aware 
subtitle: aware 
description: aware 
tags: [] 
date: 2021-01-11 11:53:03 +800 
version: 1.0
formatter: on
---

# Aware

## 简介

**`Aware` 是 Spring 提供的 Bean 扩展点之一，用于由 Spring 通过回调的方式获取特定的框架对象。**

## 定义

```java
package org.springframework.beans.factory;

public interface Aware {

}
```

## 内置 Aware

|             Aware              |           回调方法           |         框架对象          |
| :----------------------------: | :--------------------------: | :-----------------------: |
|         BeanNameAware          |         setBeanName          |         BeanName          |
|      BeanClassLoaderAware      |      setBeanClassLoader      |        ClassLoader        |
|        BeanFactoryAware        |        setBeanFactory        |        BeanFactory        |
|        EnvironmentAware        |        setEnvironment        |        Environment        |
|   EmbeddedValueResolverAware   |   setEmbeddedValueResolver   |    StringValueResolver    |
|      ResourceLoaderAware       |      setResourceLoader       |      ResourceLoader       |
| ApplicationEventPublisherAware | setApplicationEventPublisher | ApplicationEventPublisher |
|       MessageSourceAware       |       setMessageSource       |       MessageSource       |
|    ApplicationContextAware     |    setApplicationContext     |    ApplicationContext     |

## 调用时机

在 Bean 的实例化过程中，Spring 通过调用两种方式来触发 `Aware`的回调：

1. 调用`AbstractAutowireCapableBeanFactory.invokeAwareMethods` 。
2. 调用 **Bean后置处理器** `ApplicationContextAwareProcessor` 的 `postProcessBeforeInitialization(result, beanName)`方法。

上述流程在 `AbstractAutowireCapableBeanFactory` 的 `initializeBean` 方法中得以体现。

### invokeAwareMethods

```java
package org.springframework.beans.factory.support;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
    implements AutowireCapableBeanFactory {

    private void invokeAwareMethods(String beanName, Object bean) {
        if (bean instanceof Aware) {
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ClassLoader bcl = getBeanClassLoader();
                if (bcl != null) {
                    ((BeanClassLoaderAware) bean).setBeanClassLoader(bcl);
                }
            }
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(AbstractAutowireCapableBeanFactory.this);
            }
        }
    }

}
```

### ApplicationContextAwareProcessor

```java

package org.springframework.context.support;

class ApplicationContextAwareProcessor implements BeanPostProcessor {

    @Override
    @Nullable
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        // 调用 Aware
        invokeAwareInterfaces(bean);

        return bean;
    }

    private void invokeAwareInterfaces(Object bean) {
        if (bean instanceof EnvironmentAware) {
            ((EnvironmentAware) bean).setEnvironment(this.applicationContext.getEnvironment());
        }
        if (bean instanceof EmbeddedValueResolverAware) {
            ((EmbeddedValueResolverAware) bean).setEmbeddedValueResolver(this.embeddedValueResolver);
        }
        if (bean instanceof ResourceLoaderAware) {
            ((ResourceLoaderAware) bean).setResourceLoader(this.applicationContext);
        }
        if (bean instanceof ApplicationEventPublisherAware) {
            ((ApplicationEventPublisherAware) bean).setApplicationEventPublisher(this.applicationContext);
        }
        if (bean instanceof MessageSourceAware) {
            ((MessageSourceAware) bean).setMessageSource(this.applicationContext);
        }
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(this.applicationContext);
        }
    }

}
```

上述流程如图所示：

![Spring Bean Aware](http://processon.com/chart_image/5fa234e3e401fd1c7b83783f.png)

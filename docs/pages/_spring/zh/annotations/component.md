---
formatter: "@formatter:off"
title: "@Component" 
subtitle: component 
summary: 标记指定的注释类为**"组件"**。 
tags: [spring,annotation] 
date: 2021-02-02 13:17:28 +800 
version: 1.0
formatter: "@formatter:on"
---

# @Component

## What

**`@Component`注解用于将指定的类标记为*组件*。**

## Usage

## How

`ClassPathBeanDefinitionScanner`类提供了对`@Component`注释的解析支持。

在此之前，有必要先引入一下`TypeFilter`的概念，那么什么是`TypeFilter`呢？

### TypeFilter

`TypeFilter`是一个类型过滤器接口，用于判定一个类的元数据是否满足条件，基定义如下：

```java
package org.springframework.core.type.filter;

@FunctionalInterface
public interface TypeFilter {

    boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException;

}
```

几个重要的内置过滤器：

* AnnotationTypeFilter

了解了`TypeFilter`的概念，现在，来看一下`ClassPathBeanDefinitionScanner`

### ClassPathBeanDefinitionScanner

在`ClassPathBeanDefinitionScanner`构造方法中，当参数`useDefaultFilters`为`true`时，会调用`registerDefaultFilters()`方法，在`ClassPathBeanDefinitionScanner
(BeanDefinitionRegistry registry)`构造方法中，`useDefaultFilters`也默认为`true`。

```java
package org.springframework.context.annotation;

public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this(registry, true);
    }

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        this(registry, useDefaultFilters, getOrCreateEnvironment(registry));
    }

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters,
        Environment environment) {

        this(registry, useDefaultFilters, environment,
            (registry instanceof ResourceLoader ? (ResourceLoader) registry : null));
    }

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters,
        Environment environment, @Nullable ResourceLoader resourceLoader) {

        Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
        this.registry = registry;

        if (useDefaultFilters) {
            //注册默认的 TypeFilter's
            registerDefaultFilters();
        }
        setEnvironment(environment);
        setResourceLoader(resourceLoader);
    }

}
```

#### registerDefaultFilters()

在父类的`registerDefaultFilters()`方法中，注册了几个基于注释的过滤器`AnnotationTypeFilter`。 除了Spring的`@Component`注释，与其同时，还尝试注册JSR-250的`@ManagedBean`和JSR-330
的`@Named`。

```java
package org.springframework.context.annotation;

public class ClassPathScanningCandidateComponentProvider implements EnvironmentCapable, ResourceLoaderAware {

    protected void registerDefaultFilters() {
        // 注册 @Component 注释过滤器
        this.includeFilters.add(new AnnotationTypeFilter(Component.class));
        ClassLoader cl = ClassPathScanningCandidateComponentProvider.class.getClassLoader();
        try {
            // 注册 @ManagedBean 注释过滤器
            this.includeFilters.add(new AnnotationTypeFilter(
                ((Class<? extends Annotation>) ClassUtils.forName("javax.annotation.ManagedBean", cl)), false));
            logger.trace("JSR-250 'javax.annotation.ManagedBean' found and supported for component scanning");
        } catch (ClassNotFoundException ex) {
            // JSR-250 1.1 API (as included in Java EE 6) not available - simply skip.
        }
        try {
            // 注册 @Named 注释过滤器
            this.includeFilters.add(new AnnotationTypeFilter(
                ((Class<? extends Annotation>) ClassUtils.forName("javax.inject.Named", cl)), false));
            logger.trace("JSR-330 'javax.inject.Named' annotation found and supported for component scanning");
        } catch (ClassNotFoundException ex) {
            // JSR-330 API not available - simply skip.
        }
    }

}
```

#### scan(basePackages)



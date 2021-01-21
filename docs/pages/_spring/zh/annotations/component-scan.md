---
formatter: "@formatter:off"
title: "@ComponentScan"
subtitle: component-scan 
summary: 组件扫描
tags: [] 
date: 2021-01-21 20:50:48 +800 
version: 1.0
formatter: "@formatter:on"
---

# @ComponentScan

## What

`@ComponentScan`注解实现`<context:component-scan>`的组件扫描指令。

## Features

* **扫描包的范围**：`basePackages`和`basePackageClasses`指定的包的合集，如果未指定，则为该注解声明类所在的包。
* **扫描的组件**：被`@Component`或其扩展注解标记的类，支持`JSR-250`的`@ManagedBean`和`JSR-330`的`@Named`。

## How

`@ComponentScan`由`ComponentScanAnnotationParser`类提供解析处理，方法为`parse()`。

### ComponentScanAnnotationParser

```java
package org.springframework.context.annotation;

class ComponentScanAnnotationParser {

    public Set<BeanDefinitionHolder> parse(AnnotationAttributes componentScan, final String declaringClass) {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(this.registry,
                componentScan.getBoolean("useDefaultFilters"), this.environment, this.resourceLoader);

        Class<? extends BeanNameGenerator> generatorClass = componentScan.getClass("nameGenerator");
        boolean useInheritedGenerator = (BeanNameGenerator.class == generatorClass);
        scanner.setBeanNameGenerator(useInheritedGenerator ? this.beanNameGenerator :
                BeanUtils.instantiateClass(generatorClass));

        ScopedProxyMode scopedProxyMode = componentScan.getEnum("scopedProxy");
        if (scopedProxyMode != ScopedProxyMode.DEFAULT) {
            scanner.setScopedProxyMode(scopedProxyMode);
        } else {
            Class<? extends ScopeMetadataResolver> resolverClass = componentScan.getClass("scopeResolver");
            scanner.setScopeMetadataResolver(BeanUtils.instantiateClass(resolverClass));
        }

        scanner.setResourcePattern(componentScan.getString("resourcePattern"));

        for (AnnotationAttributes filter : componentScan.getAnnotationArray("includeFilters")) {
            for (TypeFilter typeFilter : typeFiltersFor(filter)) {
                scanner.addIncludeFilter(typeFilter);
            }
        }
        for (AnnotationAttributes filter : componentScan.getAnnotationArray("excludeFilters")) {
            for (TypeFilter typeFilter : typeFiltersFor(filter)) {
                scanner.addExcludeFilter(typeFilter);
            }
        }

        boolean lazyInit = componentScan.getBoolean("lazyInit");
        if (lazyInit) {
            scanner.getBeanDefinitionDefaults().setLazyInit(true);
        }

        // 解析要扫描的包，取 basePackages 和 basePackageClasses 指定的包的合集，如果未指定，则取 declaringClass 所在的包。
        Set<String> basePackages = new LinkedHashSet<>();
        String[] basePackagesArray = componentScan.getStringArray("basePackages");
        for (String pkg : basePackagesArray) {
            String[] tokenized = StringUtils.tokenizeToStringArray(this.environment.resolvePlaceholders(pkg),
                    ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
            Collections.addAll(basePackages, tokenized);
        }
        for (Class<?> clazz : componentScan.getClassArray("basePackageClasses")) {
            basePackages.add(ClassUtils.getPackageName(clazz));
        }
        if (basePackages.isEmpty()) {
            basePackages.add(ClassUtils.getPackageName(declaringClass));
        }

        scanner.addExcludeFilter(new AbstractTypeHierarchyTraversingFilter(false, false) {

            @Override
            protected boolean matchClassName(String className) {
                return declaringClass.equals(className);
            }
        });
        return scanner.doScan(StringUtils.toStringArray(basePackages));
    }

}
```

41-53行解析扫描包的范围由属性`basePackages`和`basePackageClasses`指定的包的合集，如果未指定，则为该注解声明类所在的包。
![](../images/annotations/component-scan-packages.png)

## Options

| option             | 类型     | 说明 | 默认 |
| ------------------ | -------- | ---- | ---- |
| value              | String[] |      | {}   |
| basePackages       | String[] |      | {}   |
| basePackageClasses | Class[]  |      | {}   |
| nameGenerator      |          |      |      |
| scopeResolver      |          |      |      |
| scopedProxy        |          |      |      |
| resourcePattern    |          |      |      |
| useDefaultFilters  |          |      |      |
| includeFilters     |          |      |      |
| excludeFilters     |          |      |      |
| lazyInit           |          |      |      |
|                    |          |      |      |
|                    |          |      |      |
|                    |          |      |      |


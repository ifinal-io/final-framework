---
formatter: "@formatter:off"
title: "@Bean" 
subtitle: bean 
summary: bean 
tags: [spring,annotation] 
date: 2021-01-24 12:12:22 +800 
version: 1.0
formatter: "@formatter:on"
---

# Bean

## What

`@Bean`注解用于标记一个`Method`产生`BeanDefinition`。

## Usage



## How

### Retrieve Bean Method

在`doProcessConfigurationClass()`方法中，有一段检索`BeanMethod`的代码片段：

```java
// Process individual @Bean methods
Set<MethodMetadata> beanMethods = retrieveBeanMethodMetadata(sourceClass);
for (MethodMetadata methodMetadata : beanMethods) {
	configClass.addBeanMethod(new BeanMethod(methodMetadata, configClass));
}
```

### Load

在`loadBeanDefinitionsForBeanMethod()`中加载`BeanDefinition`
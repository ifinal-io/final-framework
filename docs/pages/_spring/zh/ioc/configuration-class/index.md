---
formatter: "@formatter:off"
title: ConfigurationClass
subtitle: configuration-class 
summary: 含有特殊标记的`BeanDefiniton`。
tags: [spring,ioc] 
date: 2021-01-22 16:49:09 +800 
version: 1.0
formatter: "@formatter:on"
---

# ConfigurationClass

## What

**配置类(`ConfigurationClass`)是一种含有特殊标记的`BeanDefiniton`。**

## Definition

Spring将符合以下规则的`BeanDefinition`称为**配置类(`ConfigurationClass`)**：

* 不含有工厂方法
* 非以下接口子类：
    * BeanFactoryPostProcessor
    * BeanPostProcessor
    * AopInfrastructureBean
    * EventListenerFactory
* 被以下注解标记：
    * Configuration
    * Component
    * ComponentScan
    * Import
    * ImportResource



## Target

从本节开始，将通过源码分析的方式解决以下问题：

* 如何判定一个`BeanDefinition`是否是`ConfigurationClass`；
* 如何解析一个`ConfigurationClass`；
* `ConfigurationClass`是何时被解析的。

## How

如何判定一个`BeanDefinition`是否是配置类呢？

Spring提供了工具类`ConfigurationClassUtils`的`checkConfigurationClassCandidate()`方法来检测一个`BeanDefinition`是否是配置类。

该方法中有几个核心的代码片段：

* 含有工厂方法名的返回`false`

```java
if(className==null||beanDef.getFactoryMethodName()!=null){
    return false;
}
```

* 实现了特定接口的返回`false`

```java
if(BeanFactoryPostProcessor.class.isAssignableFrom(beanClass)||
    BeanPostProcessor.class.isAssignableFrom(beanClass)||
    AopInfrastructureBean.class.isAssignableFrom(beanClass)||
    EventListenerFactory.class.isAssignableFrom(beanClass)){
    return false;
}
```

* 含有特定注解的返回`true`

```java
Map<String, Object> config=metadata.getAnnotationAttributes(Configuration.class.getName());
if(config!=null&&!Boolean.FALSE.equals(config.get("proxyBeanMethods"))){
    beanDef.setAttribute(CONFIGURATION_CLASS_ATTRIBUTE,CONFIGURATION_CLASS_FULL);
}else if(config!=null||isConfigurationCandidate(metadata)){
    beanDef.setAttribute(CONFIGURATION_CLASS_ATTRIBUTE,CONFIGURATION_CLASS_LITE);
}else{
    return false;
}
```

```java
package org.springframework.context.annotation;

abstract class ConfigurationClassUtils {

    public static boolean checkConfigurationClassCandidate(
            BeanDefinition beanDef, MetadataReaderFactory metadataReaderFactory) {

        // 不含有工厂方法
        String className = beanDef.getBeanClassName();
        if (className == null || beanDef.getFactoryMethodName() != null) {
            return false;
        }

        //
        AnnotationMetadata metadata;
        if (beanDef instanceof AnnotatedBeanDefinition &&
                className.equals(((AnnotatedBeanDefinition) beanDef).getMetadata().getClassName())) {
            // Can reuse the pre-parsed metadata from the given BeanDefinition...
            metadata = ((AnnotatedBeanDefinition) beanDef).getMetadata();
        } else if (beanDef instanceof AbstractBeanDefinition && ((AbstractBeanDefinition) beanDef).hasBeanClass()) {
            // Check already loaded Class if present...
            // since we possibly can't even load the class file for this Class.
            Class<?> beanClass = ((AbstractBeanDefinition) beanDef).getBeanClass();
            if (BeanFactoryPostProcessor.class.isAssignableFrom(beanClass) ||
                    BeanPostProcessor.class.isAssignableFrom(beanClass) ||
                    AopInfrastructureBean.class.isAssignableFrom(beanClass) ||
                    EventListenerFactory.class.isAssignableFrom(beanClass)) {
                return false;
            }
            metadata = AnnotationMetadata.introspect(beanClass);
        } else {
            try {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(className);
                metadata = metadataReader.getAnnotationMetadata();
            } catch (IOException ex) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Could not find class file for introspecting configuration annotations: " +
                            className, ex);
                }
                return false;
            }
        }

        Map<String, Object> config = metadata.getAnnotationAttributes(Configuration.class.getName());
        if (config != null && !Boolean.FALSE.equals(config.get("proxyBeanMethods"))) {
            beanDef.setAttribute(CONFIGURATION_CLASS_ATTRIBUTE, CONFIGURATION_CLASS_FULL);
        } else if (config != null || isConfigurationCandidate(metadata)) {
            beanDef.setAttribute(CONFIGURATION_CLASS_ATTRIBUTE, CONFIGURATION_CLASS_LITE);
        } else {
            return false;
        }

        // It's a full or lite configuration candidate... Let's determine the order value, if any.
        Integer order = getOrder(metadata);
        if (order != null) {
            beanDef.setAttribute(ORDER_ATTRIBUTE, order);
        }

        return true;
    }

    /**
     * Check the given metadata for a configuration class candidate
     * (or nested component class declared within a configuration/component class).
     * @param metadata the metadata of the annotated class
     * @return {@code true} if the given class is to be registered for
     * configuration class processing; {@code false} otherwise
     */
    public static boolean isConfigurationCandidate(AnnotationMetadata metadata) {
        // Do not consider an interface or an annotation...
        if (metadata.isInterface()) {
            return false;
        }

        // Any of the typical annotations found?
        for (String indicator : candidateIndicators) {
            if (metadata.isAnnotated(indicator)) {
                return true;
            }
        }

        // Finally, let's look for @Bean methods...
        try {
            return metadata.hasAnnotatedMethods(Bean.class.getName());
        } catch (Throwable ex) {
            if (logger.isDebugEnabled()) {
                logger.debug("Failed to introspect @Bean methods on class [" + metadata.getClassName() + "]: " + ex);
            }
            return false;
        }
    }

}
```


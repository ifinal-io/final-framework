---
formatter: "@formatter:off"
title: ConfigurationClass
subtitle: configuration-class 
summary: configuration-class 
tags: [] 
date: 2021-01-22 16:49:09 +800 
version: 1.0
formatter: "@formatter:on"
---

# ConfigurationClass

## What

**`ConfigurationClass`是一种用户定义的配置类，包含被`@Bean`标记的方法。**

具体体现为：

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

本文将通过源码的方式，解决以下问题：

* 如何判定一个`BeanDefinition`是否是`ConfigurationClass`；
* 如何解析一个`ConfigurationClass`。

## How

首先，来解决第一个问题：如何判定一个`BeanDefinition`是否是`ConfigurationClass`？

### How to Check

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

拉下来，再来分析一下是如何解析的。

### How to Parse

Spring提供了专用于解析`ConfigurationClass`的工具类`ConfigurationClassParser`。

在`ConfigurationClassParser`的`parse(Set<BeanDefinitionHolder> configCandidates)`方法中，定义了处理的流程：

```java
public void parse(Set<BeanDefinitionHolder> configCandidates) {
    for (BeanDefinitionHolder holder : configCandidates) {
        BeanDefinition bd = holder.getBeanDefinition();
        try {
            if (bd instanceof AnnotatedBeanDefinition) {
                parse(((AnnotatedBeanDefinition) bd).getMetadata(), holder.getBeanName());
            }
            else if (bd instanceof AbstractBeanDefinition && ((AbstractBeanDefinition) bd).hasBeanClass()) {
                parse(((AbstractBeanDefinition) bd).getBeanClass(), holder.getBeanName());
            }
            else {
                parse(bd.getBeanClassName(), holder.getBeanName());
            }
        }
        catch (BeanDefinitionStoreException ex) {
            throw ex;
        }
        catch (Throwable ex) {
            throw new BeanDefinitionStoreException(
                    "Failed to parse configuration class [" + bd.getBeanClassName() + "]", ex);
        }
    }

    this.deferredImportSelectorHandler.process();
}
```

通过分析`BeanDefinition`的类型，分别处理，但最终都走到`processConfigurationClass(ConfigurationClass configClass, Predicate<String> filter)`方法。

在该方法中，将`ConfigurationClass`转化成一个`SourceClass`，循环处理当前类及其父类和接口：

```java
// Recursively process the configuration class and its superclass hierarchy.
SourceClass sourceClass = asSourceClass(configClass, filter);
do {
	sourceClass = doProcessConfigurationClass(configClass, sourceClass, filter);
}
while (sourceClass != null);
```

接下来，继续分析`doProcessConfigurationClass`方法，在该方法中，将逐个分析类上的注解。

首先分析的是`@Component`注解：

```java
if (configClass.getMetadata().isAnnotated(Component.class.getName())) {
	// Recursively process any member (nested) classes first
	processMemberClasses(configClass, sourceClass, filter);
}
```

然后，处理`@PropertySources`注解：

```java
// Process any @PropertySource annotations
for (AnnotationAttributes propertySource : AnnotationConfigUtils.attributesForRepeatable(
		sourceClass.getMetadata(), PropertySources.class,
		org.springframework.context.annotation.PropertySource.class)) {
	if (this.environment instanceof ConfigurableEnvironment) {
		processPropertySource(propertySource);
	}
	else {
		logger.info("Ignoring @PropertySource annotation on [" + sourceClass.getMetadata().getClassName() +
				"]. Reason: Environment must implement ConfigurableEnvironment");
	}
}
```

其次，处理`@ComponentScan`注解：

```java
// Process any @ComponentScan annotations
Set<AnnotationAttributes> componentScans = AnnotationConfigUtils.attributesForRepeatable(
		sourceClass.getMetadata(), ComponentScans.class, ComponentScan.class);
if (!componentScans.isEmpty() &&
		!this.conditionEvaluator.shouldSkip(sourceClass.getMetadata(), ConfigurationPhase.REGISTER_BEAN)) {
	for (AnnotationAttributes componentScan : componentScans) {
		// The config class is annotated with @ComponentScan -> perform the scan immediately
		Set<BeanDefinitionHolder> scannedBeanDefinitions =
				this.componentScanParser.parse(componentScan, sourceClass.getMetadata().getClassName());
		// Check the set of scanned definitions for any further config classes and parse recursively if needed
		for (BeanDefinitionHolder holder : scannedBeanDefinitions) {
			BeanDefinition bdCand = holder.getBeanDefinition().getOriginatingBeanDefinition();
			if (bdCand == null) {
				bdCand = holder.getBeanDefinition();
			}
			if (ConfigurationClassUtils.checkConfigurationClassCandidate(bdCand, this.metadataReaderFactory)) {
				parse(bdCand.getBeanClassName(), holder.getBeanName());
			}
		}
	}
}
```




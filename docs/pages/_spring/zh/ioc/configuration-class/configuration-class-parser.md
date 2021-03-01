---
formatter: "@formatter:off"
title: ConfigurationParser
subtitle: configuration-class-parser 
summary: configuration-class-parser 
tags: [] 
date: 2021-02-25 21:37:50 +800 
version: 1.0
formatter: "@formatter:on"
---

# ConfigurationParser 


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




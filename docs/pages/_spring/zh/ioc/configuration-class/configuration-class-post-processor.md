---
formatter: "@formatter:off"
title: ConfigurationClassPostProcessor
subtitle: configuration-class-post-processor 
summary: configuration-class-post-processor 
tags: [] 
date: 2021-02-04 21:14:34 +800 
version: 1.0
formatter: "@formatter:on"
---


# ConfigurationClassPostProcessor

## What

`ConfigurationClassPostProcessor`是`BeanFactoryPostProcessor`的一个实现类，并且实现了`PriorityOrdered`接口，如下图所示：

```mermaid
classDiagram
    BeanFactoryPostProcessor <|-- BeanDefinitionRegistryPostProcessor
    BeanDefinitionRegistryPostProcessor <|-- ConfigurationClassPostProcessor
    PriorityOrdered <|-- ConfigurationClassPostProcessor
```

## How

在`ConfigurableApplicationContext`的`refresh()`过程中，`postProcessBeanDefinitionRegistry()`和`postProcessBeanFactory()`方法被依次回调，触发`processConfigBeanDefinitions()`的执行，流程如下图所示：

```mermaid
flowchart TD
	a1["postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)"]
    a2["postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)"]
    subgraph one["processConfigBeanDefinitions(registry)"]
    b1["String[] candidateNames = registry.getBeanDefinitionNames();"]
    b2["ConfigurationClassUtils.checkConfigurationClassCandidate(beanDef, this.metadataReaderFactory)"]
	b3["configCandidates.sort((bd1, bd2))"]    
    b1-->b2-->b3
    end
    a1 --> one
    a2 --> one
```

在`processConfigBeanDefinitions()`方法中，主要有两个功能：

1. 从`BeanDefinitionRegistry`找到符合`ConfigurationClass`定义的`BeanDefinition`并排序;
2. 使用`ConfigurationClassParser`解析`ConfigurationClass`。
   
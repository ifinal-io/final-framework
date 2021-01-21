---
formatter: "@formatter:off"
title: bean-definition 
subtitle: bean-definition 
summary: bean-definition 
tags: [] 
date: 2021-01-19 20:38:24 +800 
version: 1.0
formatter: "@formatter:on"
---

# bean-definition

![BeanDefinition](../images/context/bean-definition.png)

```mermaid
classDiagram
    BeanDefinition <|-- AbstractBeanDefinition
    BeanDefinition <|-- AnnotatedBeanDefinition
    AbstractBeanDefinition <|-- RootBeanDefinition
    AbstractBeanDefinition <|-- ChildBeanDefinition
    AbstractBeanDefinition <|-- GenericBeanDefinition
    GenericBeanDefinition <|-- AnnotatedGenericBeanDefinition
    AnnotatedBeanDefinition <|-- AnnotatedGenericBeanDefinition
    AnnotatedBeanDefinition <|-- ScannedGenericBeanDefinition
    GenericBeanDefinition <|-- ScannedGenericBeanDefinition

```


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

## 定义

```java
package org.springframework.context;

public interface ConfigurableApplicationContext extends ApplicationContext, Lifecycle, Closeable {

    /**
     * Load or refresh the persistent representation of the configuration, which
     * might be from Java-based configuration, an XML file, a properties file, a
     * relational database schema, or some other format.
     * <p>As this is a startup method, it should destroy already created singletons
     * if it fails, to avoid dangling resources. In other words, after invocation
     * of this method, either all or no singletons at all should be instantiated.
     * @throws BeansException if the bean factory could not be initialized
     * @throws IllegalStateException if already initialized and multiple refresh
     * attempts are not supported
     */
    void refresh() throws BeansException, IllegalStateException;

}
```
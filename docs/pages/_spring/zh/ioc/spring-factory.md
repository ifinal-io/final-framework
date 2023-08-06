---
formatter: "@formatter:off"
title: SpringFactory
subtitle: spring-factory 
summary: Spring 框架内部使用的通用的工厂加载机制
tags: [spring,ioc,spi] 
date: 2021-02-24 11:02:00 +800 
version: 1.0
formatter: "@formatter:on"
---

# SpringFactory

## What

**`SpringFactory` 是Spring框架内部使用的一种通用的工厂加载机制，用于加载`META-INF/spring.factories`文件内的SPI扩展。**

## Features

`SpringFactoriesLoader`是Spring内置的加载`META-INF/spring.factories`文件的工具类，有两个方法：

* **`loadFactoryNames(Class factoryType,ClassLoader classLoader)`**：加载指定工厂类的实现类名称。
* **`loadFactories(Class factoryType,ClassLoader classLoader)`**：实例化指定工厂类的实现类。

其中，`loadFactories()`的结果会根据`Ordered`接口及`@Order`和`@Priority`注解指定的`order`值进行排序。

> `META-INF/spring.factories`本质是一个`properties`类型的文件，存储格式为`key=values`，多个value之间用英文‘`,`’分隔。

## Usage

* 加载`ApplicationListener`的实现类名称：

```java
@Slf4j(topic = "springFactory")
class SpringFactoriesLoaderTest {

    @Test
    void loadApplicationListenerFactoryNames(){
        List<String> factoryNames = SpringFactoriesLoader.loadFactoryNames(ApplicationListener.class,null);
        factoryNames.forEach(it -> logger.info("{}",it));
    }
    
}
```

运行输出：

```shell
10:55:26.316 [main] INFO springFactory - org.springframework.boot.ClearCachesApplicationListener
10:55:26.319 [main] INFO springFactory - org.springframework.boot.builder.ParentContextCloserApplicationListener
10:55:26.319 [main] INFO springFactory - org.springframework.boot.cloud.CloudFoundryVcapEnvironmentPostProcessor
10:55:26.319 [main] INFO springFactory - org.springframework.boot.context.FileEncodingApplicationListener
10:55:26.319 [main] INFO springFactory - org.springframework.boot.context.config.AnsiOutputApplicationListener
10:55:26.319 [main] INFO springFactory - org.springframework.boot.context.config.ConfigFileApplicationListener
10:55:26.319 [main] INFO springFactory - org.springframework.boot.context.config.DelegatingApplicationListener
10:55:26.319 [main] INFO springFactory - org.springframework.boot.context.logging.ClasspathLoggingApplicationListener
10:55:26.319 [main] INFO springFactory - org.springframework.boot.context.logging.LoggingApplicationListener
10:55:26.319 [main] INFO springFactory - org.springframework.boot.liquibase.LiquibaseServiceLocatorApplicationListener
10:55:26.320 [main] INFO springFactory - org.springframework.boot.autoconfigure.BackgroundPreinitializer

Process finished with exit code 0
```

* 加载`ApplicationListener`的实现类：

```java
@Slf4j(topic = "springFactory")
class SpringFactoriesLoaderTest {

    @Test
    void loadApplicationListenerFactories(){
        List<ApplicationListener> listeners = SpringFactoriesLoader.loadFactories(ApplicationListener.class, null);
        listeners.forEach(it -> logger.info("{}",it.getClass()));
    }
    
}
```

运行输出：

```shell
11:00:47.488 [main] INFO springFactory - class org.springframework.boot.cloud.CloudFoundryVcapEnvironmentPostProcessor
11:00:47.491 [main] INFO springFactory - class org.springframework.boot.context.config.ConfigFileApplicationListener
11:00:47.491 [main] INFO springFactory - class org.springframework.boot.context.config.AnsiOutputApplicationListener
11:00:47.491 [main] INFO springFactory - class org.springframework.boot.context.logging.LoggingApplicationListener
11:00:47.491 [main] INFO springFactory - class org.springframework.boot.context.logging.ClasspathLoggingApplicationListener
11:00:47.491 [main] INFO springFactory - class org.springframework.boot.autoconfigure.BackgroundPreinitializer
11:00:47.491 [main] INFO springFactory - class org.springframework.boot.context.config.DelegatingApplicationListener
11:00:47.491 [main] INFO springFactory - class org.springframework.boot.builder.ParentContextCloserApplicationListener
11:00:47.491 [main] INFO springFactory - class org.springframework.boot.ClearCachesApplicationListener
11:00:47.491 [main] INFO springFactory - class org.springframework.boot.context.FileEncodingApplicationListener
11:00:47.491 [main] INFO springFactory - class org.springframework.boot.liquibase.LiquibaseServiceLocatorApplicationListener

Process finished with exit code 0
```






---
formatter: "@formatter:off"
title: 你好，世界
subtitle: component-scan 
summary: 程序员世界的大门。
tags: [] 
date: 2021-01-13 22:31:05 +800 
version: 1.0
formatter: "@formatter:on"
---

# 你好，世界

## 简介

本文将通过程序员入门必经之路 `Hello World` ，引导读者走进 `Spring Boot`的世界。

## Hello World

在`Spring Boot`大行其道的现在，姑且认为各位读者都写过类似下面这段代码：

```java
package org.ifinal.finalframework.example;

@SpringBootApplication
@RestController
public class FinalApplication {

    public static void main(final String[] args) {
        SpringApplication.run(FinalApplication.class);
    }

    @GetMapping("/helloworld")
    public String hello() {
        return "Hello World";
    }

}
```

当运行`main`方法，并访问`http://localhost:8080/helloworld`时，是的，就这么简单的几行代码，一个基于 `Spring Boot` 的 `Hello World` 应用就创建好了。

然后再想想之前的`Spring MVC`时代的各种配置……

我想，此刻，与首次听说`Spring Boot`时的百般拒绝，和第一次使用 `Spring Boot` 时那无比波澜的小心脏，应该只有简短几句："**哎呦！卧槽！真香！**"能概述了。

![真香](https://imgsa.baidu.com/forum/w%3D580/sign=503c490c02b30f24359aec0bf894d192/00edb8b7d0a20cf41a181cb27b094b36adaf9946.jpg)

## 真香定律

刚刚诠释了一下"真香定律"，现在是时候来分析一下为什么是真的香了。

现在，让我们回到上面那短短的几行代码，都做了些什么：

1. 创建了一个`FinalApplication`类；
2. 在类上声明了一个`@SpringBootApplication`注解；
3. 在`main`方法写了一行代码：`SpringApplication.run(FinalApplication.class);`
4. 声明了一个`/helloworld`的接口。

这4点中，除了1和4看着眼熟之外，2和3是新接触的内容，我们先查看`@SpringBootApplication`的源码，发现有比较熟悉的代码`@ComponentScan`。

```java
package org.springframework.boot.autoconfigure;

@ComponentScan(excludeFilters = {@Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
    @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class)})
public @interface SpringBootApplication {

    @AliasFor(annotation = ComponentScan.class, attribute = "basePackages")
    String[] scanBasePackages() default {};

    @AliasFor(annotation = ComponentScan.class, attribute = "basePackageClasses")
    Class<?>[] scanBasePackageClasses() default {};

    @AliasFor(annotation = ComponentScan.class, attribute = "nameGenerator")
    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

}
```

`@ComponentScan`实现了在 `Spring MVC` 时代的 Spring XML 配置文件 `<context:component-scan>` 节点的Java代码支持，再加上 `@AliasFor` 注解，暂且可以断定
`@SpringBootApplication` 启到了相同的作用，实现了组件扫描的功能。那么剩下的`SpringApplication.run(FinalApplication.class);`应该就是启动 Spring 容器 并初始化 WEB 环境了。

> 事实上也确实如此。

## 结语

本文通过一个简单了`Hello World`示例，向读者展示了`Spring Boot`相对于传统`Spring MVC`优势的**冰山一角**。

在后续的章节中，将通过演示一些在工作中应用的真实案例，逐渐的将`Spring Boot`强大的封装展示给各位读者，并通过解读一些核心源码，了解其设计思想与实现原理，与各位一起学习，共同进步！

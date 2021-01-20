---
formatter: off
title: SpringApplicationRunListener
subtitle: spring-application-run-listener 
summary: 用于监听`SpringApplication`的`run`方法的监听器
tags: [spring,application,listener] 
date: 2021-01-16 19:53:41 +800 
version: 1.0
formatter: on
---

# SpringApplicationRunListener

## 概述

**`SpringApplicationRunListener`是Spring提供的用于监听`SpringApplication`的`run`方法的监听器。**

[Spring Application 启动流程](spring-application.md)一文中，在分析`run`方法源码时，得知`SpringApplication`会通过`SpringFactoriesLoader`
加载声明在`/META-INF/spring.factories`配置文件中的`SpringApplicationRunListener`并实例化，然后传递给`SpringApplicationRunListeners`对象，在`run`
方法的执行过程中，通过`SpringApplicationRunListeners`间隔地调用所有`SpringApplicationRunListener`的方法。详情请查阅`SpringApplicationRunListeners`相关源码。

* `SpringApplicationRunListener`由`SpringApplicaton`在`run`方法是通过`SpringFactoriesLoader`从`/META-INF/spring.factories`中加载。
* `SpringApplicationRunListener`应该声明一个接收`SpringApplication`和`String[]`参数的公开的构造函数。

## 定义

```java
package org.springframework.boot;

public interface SpringApplicationRunListener {

    default void starting() {
    }

    default void environmentPrepared(ConfigurableEnvironment environment) {
    }

    default void contextPrepared(ConfigurableApplicationContext context) {
    }

    default void contextLoaded(ConfigurableApplicationContext context) {
    }

    default void started(ConfigurableApplicationContext context) {
    }

    default void running(ConfigurableApplicationContext context) {
    }

    default void failed(ConfigurableApplicationContext context, Throwable exception) {
    }

}
```

## EventPublishingRunListener

`EventPublishingRunListener`是Spring提供的内置实现，用于监听SpringApplication运行状态并发布运行事件`SpringApplicationEvent`。

```java
package org.springframework.boot.context.event;

public class EventPublishingRunListener implements SpringApplicationRunListener, Ordered {

    private final SpringApplication application;

    private final String[] args;

    private final SimpleApplicationEventMulticaster initialMulticaster;

    public EventPublishingRunListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
        this.initialMulticaster = new SimpleApplicationEventMulticaster();
        for (ApplicationListener<?> listener : application.getListeners()) {
            this.initialMulticaster.addApplicationListener(listener);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void starting() {
        this.initialMulticaster.multicastEvent(new ApplicationStartingEvent(this.application, this.args));
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        this.initialMulticaster
            .multicastEvent(new ApplicationEnvironmentPreparedEvent(this.application, this.args, environment));
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        this.initialMulticaster
            .multicastEvent(new ApplicationContextInitializedEvent(this.application, this.args, context));
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        for (ApplicationListener<?> listener : this.application.getListeners()) {
            if (listener instanceof ApplicationContextAware) {
                ((ApplicationContextAware) listener).setApplicationContext(context);
            }
            context.addApplicationListener(listener);
        }
        this.initialMulticaster.multicastEvent(new ApplicationPreparedEvent(this.application, this.args, context));
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        context.publishEvent(new ApplicationStartedEvent(this.application, this.args, context));
        AvailabilityChangeEvent.publish(context, LivenessState.CORRECT);
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        context.publishEvent(new ApplicationReadyEvent(this.application, this.args, context));
        AvailabilityChangeEvent.publish(context, ReadinessState.ACCEPTING_TRAFFIC);
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        ApplicationFailedEvent event = new ApplicationFailedEvent(this.application, this.args, context, exception);
        if (context != null && context.isActive()) {
            // Listeners have been registered to the application context so we should
            // use it at this point if we can
            context.publishEvent(event);
        } else {
            // An inactive context may not have a multicaster so we use our multicaster to
            // call all of the context's listeners instead
            if (context instanceof AbstractApplicationContext) {
                for (ApplicationListener<?> listener : ((AbstractApplicationContext) context)
                    .getApplicationListeners()) {
                    this.initialMulticaster.addApplicationListener(listener);
                }
            }
            this.initialMulticaster.setErrorHandler(new LoggingErrorHandler());
            this.initialMulticaster.multicastEvent(event);
        }
    }

    private static class LoggingErrorHandler implements ErrorHandler {

        private static final Log logger = LogFactory.getLog(EventPublishingRunListener.class);

        @Override
        public void handleError(Throwable throwable) {
            logger.warn("Error calling ApplicationEventListener", throwable);
        }

    }

}
```

从上述代码中可以发现，Spring内置了以下`SpringApplicationEvent`：

* 内置 Spring 应用事件

| SpringApplicationEvent              | 触发顺序 |         含义         |
| ----------------------------------- | :------: | :------------------: |
| ApplicationStartingEvent            |    1     |       启动事件       |
| ApplicationEnvironmentPreparedEvent |    2     |     环境准备事件     |
| ApplicationContextInitializedEvent  |    3     | 应用上下文初始化事件 |
| ApplicationPreparedEvent            |    4     |       准备事件       |
| ApplicationStartedEvent             |    5     |     启动完成事件     |
| ApplicationReadyEvent               |    6     |     准备完成事件     |
| ApplicationFailedEvent              |    -1    |       失败事件       |







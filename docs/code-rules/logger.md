---
layout: post
title: logger
subtitle: logger
description: logger
tags: []
menus:
    - logger
date: 2020-11-10 20:19:34 +800
version: 1.0
---
    
# logger  

## 框架  

使用`slf4j`日志框架进行日志输出，可使用`@Slf4j`注解或静态变量格式，日志变量名为`logger`。

* 静态变量

```java
    private static final Logger logger = LoggerFactory.getLogger(XXX.class);
```

* @Slf4j

在`classpath`下添加`lombok.config`

```properties
lombok.log.fieldName = logger
```

## 输出日志



在输出`info`级别以下的日志是，先判断日志是否可以输出。

```java

public class LoggerDemo{
    private static final Logger logger = LoggerFactory.getLogger(XXX.class);

    public void loggerInfo(String msg){
        if (logger.isInfoEnabled()) {
            logger.info("{}", msg);
        }
    }

}
```

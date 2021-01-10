---
formatter: off
layout: post
title: 接收 Json 参数 
subtitle: receive-json-param 
description: receive-json-param 
tags: [] 
date: 2021-01-07 14:57:05 +800 
version: 1.0
formatter: on
---

# 接收 Json 参数

## 什么是 Json 参数

**Json 参数**异于传统表单，传统表单传输的参数一般为简单类型，而 Json 参数表示的是以表单的形式传输`Json`格式的参数。如：

```
http://localhost:8080/params/person?person={"name": "xiaoming","age": 18}
```

## 如何接收

### 定义参数对象

定义一个用于接收`Json`的参数对象，如`Person`:

```java
import lombok.Data;

@Data
public class Person {

    private String name;

    private Integer age;

}
```

### 定义 Controller

定义一个`Controller` 使用`Person`接收`Json`参数，并在参数`Person`上声明注解`@RequestJsonParam`。

```java
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GettingMapping;

@RestController
@RequestMapping("/params")
public class RequestJsonController {

    @GettingMapping("/person")
    public Person person(@RequestJsonParam Person person) {
        return person;
    }

}
```

> `@RequestJsonParam` 像 `@RequestBody` 同样支持Json请求体。
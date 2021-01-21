---
formatter: "@formatter:off"
layout: post
title: Receive Json Param 
subtitle: receive-json-param 
description: receive-json-param 
tags: [] 
date: 2021-01-07 14:57:05 +800 
version: 1.0
formatter: "@formatter:on"
---

# Receive Json Param

## What

How to receive the request param value When it is not a simple type, such as like a json.

```
http://localhost:8080/params/person?person={"name": "xiaoming","age": 18}
```

## How

### Define Param Bean

Define a bean for receive the json param, such as `Person`:

```java
import lombok.Data;

@Data
public class Person {

    private String name;

    private Integer age;

}
```

### Define Controller

Define one `Controller` which have a method use `Person` to receive the json param, and add a `@RequestJsonParam` annotation on the `Person`
parameter.

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

> The `@RequestJsonParam` is also support json body like `@RequestBody`.
---
formatter: "@formatter:off"
layout: post
title: Start Entity
subtitle: how-to-define-an-entity
description: how-to-define-an-entity
tags: []
date: 2020-11-27 12:54:17 +800
version: 1.0
formatter: "@formatter:on"
---

# Start Entity

## What

`final-entity` define the rule of `ORM`(**Object/Relational Mapping**).

## How

### Import Dependency

* maven

```xml
<!-- https://mvnrepository.com/artifact/org.ifinal.finalframework.starter/final-boot-starter-entity -->
<dependency>
    <groupId>org.ifinalframework.starter</groupId>
    <artifactId>final-boot-starter-entity</artifactId>
    <version>{{ site.final.version }}</version>
</dependency>
```

* gradle

```groovy
// https://mvnrepository.com/artifact/org.ifinal.finalframework.starter/final-boot-starter-entity
compile group: 'org.ifinal.finalframework.starter', name: 'final-boot-starter-entity', version: '{{ site.final.version }}'
```

### Usage

* [Define Entity](define-entity.md)
* [Define Enum](define-enum.md)

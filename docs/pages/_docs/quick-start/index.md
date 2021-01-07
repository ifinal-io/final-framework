---
formatter: off
layout: post
title: Quick Start
subtitle: Quick Start 
description: Quick Start
tags: [] 
menus: 
    - quick-start 
    - index
date: 2021-01-06 23:44:24 +800 
version: 1.0
formatter: on
---

# README

## Import Dependency

* maven parent

```xml

<parent>
    <groupId>org.ifinal.finalframework.boot</groupId>
    <artifactId>final-boot</artifactId>
    <version>{{ site.final.version }}</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```

* maven dependency

```xml

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.ifinal.finalframework.boot</groupId>
            <artifactId>final-boot</artifactId>
            <version>{{ site.final.version }}</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```
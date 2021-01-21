---
formatter: "@formatter:off"
layout: post
title: Start Final 
subtitle: start-project 
description: start-project 
tags: [] 
date: 2021-01-07 13:01:52 +800 
version: 1.0
formatter: "@formatter:on"
---

# Start Final

## What

**Final** is s simple java framework base on `spring`, `mybatis`, `redis`, `shardingSphere` like `spring`.

## How

### Create A Spring Boot Project

* Use Idea Spring Initializr

1. new project with spring initializr
   
   ![Idea Spring Initializer](../images/quick-start/idea-spring-initializr.png)

2. config new project

   ![Idea Spring New Project](../images/quick-start/idea-spring-new-project.png)

* Use Spring Initializr

Use [`start.spring.io`](https://start.spring.io) to create a spring project.

![Spring Initializr](../images/quick-start/start.spring.io.png)

### Import Dependency

Import `final-boot` dependencies into project by `replace parent` or `import pom`.

* Replace Parent

```xml
<!-- https://mvnrepository.com/artifact/org.ifinal.finalframework.starter/final-boot -->
<parent>
    <groupId>org.ifinal.finalframework.starter</groupId>
    <artifactId>final-starter</artifactId>
    <version>{{ site.final.version }}</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>
```

* Import Pom

```xml
<!-- https://mvnrepository.com/artifact/org.ifinal.finalframework.starter/final-boot -->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.ifinal.finalframework.starter</groupId>
            <artifactId>final-starter</artifactId>
            <version>{{ site.final.version }}</version>
            <scope>import</scope>
            <type>pom</type>
        </dependency>
    </dependencies>
</dependencyManagement>
```

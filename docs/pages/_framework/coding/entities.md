---
layout: post
title: entities
subtitle: entities
categories: []
tags: []
menus:
    - coding
    - entities
author: likly
date: 2019-10-29 10:28:58 +800
version: 1.0
---

# Entities

## What

[EntitiesGeneratorProcessor](/final-coding/final-coding-entity/src/main/java/org/finalframework/coding/entity/EntitiesGeneratorProcessor.java)
将实现了[`IEntity`](/final-data/final-data-core/src/main/java/org/finalframework/data/entity/IEntity.java)接口的`Class`集合，生成到`{{site.final.entities}}`文件中，
以供其他处理器处理，如[QEntity处理器](entities.md)、[Mapper处理器](mapper.md)等。

## Usage

### Maven

* Dependency

```xml
<dependency>
    <groupId>org.finalframework</groupId>
    <artifactId>final-coding-entity</artifactId>
    <version>{{site.final.version}}</version>
</dependency>
```

* plugins

```xml
<plugin>
    <artifactId>maven-remote-resources-plugin</artifactId>
    <version>1.6.0</version>
    <executions>
        <execution>
            <goals>
                <goal>bundle</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <includes>
            <include>{{site.final.entities}}</include>
        </includes>
    </configuration>
</plugin>
```
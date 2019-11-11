---
layout: post
title: DataSource
subtitle: 数据源配置
categories: []
tags: []
menus:
    - coding
    - datasource
author: likly
date: 2019-11-04 10:50:20 +800
version: 1.0
---

# DataSource


[DataSource](/final-coding/final-coding-datasource/src/main/java/org/finalframework/coding/datasource/DataSource.java)
实现在编译时生成[DataSourceProperties](/final-coding/final-coding-datasource/src/main/resources/template/datasource/dataSourceProperties.vm)
和[DataSourceAutoConfiguration](/final-coding/final-coding-datasource/src/main/resources/template/datasource/dataSourceAutoConfiguration.vm)。


## DataSourceProperties

生成的`DataSourceProerties`会被`Spring AutoConfiguration`再次处理，可生成与`spring.datasource`相同的配置文件，能够在`application`配置文件中给出相应的提示功能。

### Template

{% gist f29da41e39d41208350ddb4591171cee %}

## DataSourceAutoConfiguration

`DataSourceAutoConfiguration`结合`mybatis`的`MapperScan`，实现多数据源的配置。

### Template

{% gist d1cdd614bbeb78292ea8b7e5cebe2b0c %}

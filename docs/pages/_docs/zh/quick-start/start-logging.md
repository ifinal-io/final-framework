---
formatter: "@formatter:off"
title: start-logging 
subtitle: start-logging 
summary: start-logging 
tags: [] 
date: 2021-01-31 22:23:24 +800 
version: 1.0
formatter: "@formatter:on"
---

# start-logging

## What

更简化的日志配置。

## Usage

### Import Dependency

```xml
<dependency>
    <groupId>org.ifinal.finalframework.starter</groupId>
    <artifactId>final-boot-starter-logging</artifactId>
    <version>{{site.final.version}}</version>
</dependency>
```

### Config Logging

```yml
logging:
  pattern: 
    # 控制台 Pattern
    console: "%clr(%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %clr([ %-36X{trace} ]){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}"
    # 文件 Pattern
    file: "%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd HH:mm:ss.SSS}} ${LOG_LEVEL_PATTERN:-%5p} ${PID:- } --- [%t] %-40.40logger{39} : [ %-36X{trace} ] : %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}"
    # 文件后缀
    rolling-file-suffix: ".%d{yyyy-MM-dd}.%i"
  file:
    # 日志路径
    path: logs/app/${dmall.dmc.project-code}/${dmall.dmc.app-code}/${dmall.dmc.instanceCode}
    # 最大保留历史
    max-history: 7
    # 单个文件最大容量
    max-size: 1GB
  # 日志级别
  level:
    org.springframework: warn
    org.apache: warn
    org.ifinal.finalframework.example: debug
    org.ifinal.finalframework.example.web.controller: debug
    org.ifinal.finalframework.example.warepool.web.controller: debug
  # 日志文件
  logger:
    root: all.log
    org.ifinal.finalframework.example.warepool.dao: mapper.log
    org.ifinal.finalframework.example.warepool.service: service.log
    org.ifinal.finalframework.example.warepool.web.controller: controller.log
```
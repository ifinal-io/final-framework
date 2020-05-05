---
layout: post
title: Limit
subtitle: limit
categories: []
tags: []
menus:
    - mapper
    - fragment
    - criteria
author: likly
date: 2019-10-15 15:37:29 +800
version: 1.0
---

# Order

```xml
<sql id="sql-criteria">
    <foreach collection="criteria" item="item" separator="criteria">${item.sql}</foreach>
</sql>
```

> [SqlCriteriaFragmentXmlMapperBuilder](/final-coding/final-coding-mapper/src/main/java/org/finalframework/mybatis/coding/mapper/builder/SqlCriteriaFragmentXmlMapperBuilder.java)

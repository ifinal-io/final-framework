---
layout: post
title: Table
subtitle: table
categories: []
tags: []
menus:
    - mapper
    - fragment
    - table
author: likly
date: 2019-10-15 15:11:04 +800
version: 1.0
---

# Table

```xml
<sql id="sql-table">
    <choose>
        <when test="tableName != null">${tableName}</when>
        <otherwise>{Entity.simpleName}</otherwise>
    </choose>
</sql>
```

> [SqlTableFragmentXmlMapperBuilder](/final-coding/final-coding-mapper/src/main/java/org/finalframework/mybatis/coding/mapper/builder/SqlTableFragmentXmlMapperBuilder.java)

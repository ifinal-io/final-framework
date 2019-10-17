---
layout: post
title: Query
subtitle: query
categories: []
tags: []
menus:
    - mapper
    - fragment
    - query
author: likly
date: 2019-10-15 15:37:29 +800
version: 1.0
---

# Order

```xml
<sql id="sql-query">
    <if test="query != null">
        <bind name="criteria" value="query.criteria"/>
        <bind name="sort" value="query.sort"/>
        <bind name="limit" value="query.limit"/>
        <where>
            <include refid="sql-criteria"/>
        </where>
        <include refid="sql-order"/>
        <include refid="sql-limit"/>
    </if>
</sql>
```

> [SqlQueryFragmentXmlMapperBuilder](/final-mybatis/final-mybatis-coding/src/main/java/org/finalframework/mybatis/coding/mapper/builder/SqlQueryFragmentXmlMapperBuilder.java)
> [sql-order](order.md)
> [sql-limit](limit.md)
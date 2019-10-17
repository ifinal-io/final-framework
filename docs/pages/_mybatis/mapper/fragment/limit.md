---
layout: post
title: Limit
subtitle: limit
categories: []
tags: []
menus:
    - mapper
    - fragment
    - limit
author: likly
date: 2019-10-15 15:37:29 +800
version: 1.0
---

# Order

```xml
<sql id="sql-limit">
    <if test="limit != null">
        <trim prefix="LIMIT">
            <if test="limit.offset != null">#{limit.offset},</if>
            <if test="limit.limit != null">#{limit.limit}</if>
        </trim>
    </if>
</sql>
```

> [SqlLimitFragmentXmlMapperBuilder](/final-mybatis/final-mybatis-coding/src/main/java/org/finalframework/mybatis/coding/mapper/builder/SqlLimitFragmentXmlMapperBuilder.java)

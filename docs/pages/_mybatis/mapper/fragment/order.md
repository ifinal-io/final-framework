---
layout: page
title: Order
subtitle: order
categories: []
tags: []
menus:
    - mapper
    - fragment
    - order
author: likly
date: 2019-10-15 15:37:29 +800
version: 1.0
---

# Order

```xml
<sql id="sql-order">
    <if test="sort != null">
        <trim prefix="ORDER BY">
            <foreach collection="sort" item="order" separator=",">#{order.property.column} #{order.direction.value}</foreach>
        </trim>
    </if>
</sql>
```

> [](/final-coding/final-coding-mapper/src/main/java/org/finalframework/mybatis/coding/mapper/builder/SqlOrderFragmentXmlMapperBuilder.java)

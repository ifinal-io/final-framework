---
post: post
title: Mapper
subtitle: 持久层的常用方法封闭
description: 通用的CRUD持久化方法
categories: []
tags: []
menus:
    - mapper
layout: page
author: likly
date: 2019-03-16 18:36:54 +800
version: 1.0
---


## Sql Fragment

### Table

* sql-table

[SqlTableXmlMapperBuilder](/final-coding/final-coding-mapper/src/main/java/org/finalframework/mybatis/coding/mapper/builder/SqlTableXmlMapperBuilder.java)

```xml
<sql id="sql-table">
     <choose>
         <when test="tableName != null">${tableName}</when>
         <otherwise>{Entity}</otherwise>
     </choose>
 </sql>
```

### Where

* sql-where-id

```xml
<sql id="sql-where-id">
    <where>
        <trim prefix="id =">#{id}</trim>
    </where>
</sql>
```
> [SqlWhereIdXmlMapperBuilder](/final-coding/final-coding-mapper/src/main/java/org/finalframework/mybatis/coding/mapper/builder/SqlWhereIdXmlMapperBuilder.java)

* sql-where-ids

```xml
<sql id="sql-where-ids">
    <if test="ids != null">
        <trim prefix="id IN">
            <foreach close=")" collection="ids" item="id" open="(" separator=",">#{id}</foreach>
        </trim>
    </if>
</sql>
```
> [SqlWhereIdsXmlMapperBuilder](/final-coding/final-coding-mapper/src/main/java/org/finalframework/mybatis/coding/mapper/builder/SqlWhereIdsXmlMapperBuilder.java)

* sql-query

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

### Order

* sql-order

```xml
<sql id="sql-order">
    <if test="sort != null">
        <trim prefix="ORDER BY">
            <foreach collection="sort" item="order" separator=",">
                #{order.property.column} #{order.direction.value}
            </foreach>
        </trim>
    </if>
</sql>
```

### Limit

* sql-limit

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
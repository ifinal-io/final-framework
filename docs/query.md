---
layout: post
title: annotation-query
subtitle: annotation-query
description: annotation-query
tags: []
menus:
    - annotation-query
date: 2020-11-18 21:21:53 +800
version: 1.0
---

# annotation-query    

## Bean

```java
    @Data
    static class Bean implements IEntity<Long> {
        @AutoInc
        @PrimaryKey
        private Long id;
        private String a;
        private String b;
        private String c;
    }
```

## AndQuery 



```sql
WHERE  a = ? AND b BETWEEN ? AND ? AND c NOT IN (?,?,?)
```

```java
@Data
static class AndQuery implements IQuery {
    @Equal
    private String a;
    @NotBetween
    private BetweenValue<String> b;
    @NotIn
    private List<String> c;
}
```

```xml
<where>
    <if test="query.a != null">
        <![CDATA[ AND a = #{query.a}]]>
    </if>
    <if test="query.b != null and query.b.min != null and query.b.max != null">
        <![CDATA[ AND b BETWEEN #{query.b.min} AND #{query.b.max}]]>
    </if>
    <if test="query.c != null">
        <foreach collection="query.c" item="item" open=" AND c NOT IN (" close=")" separator=",">#{item}</foreach>
    </if>
</where>

```



## QueryAnnotation

|    Annotation     |                    SQL                     | 备注 |
| :---------------: | :----------------------------------------: | :--: |
|     `@IsNull`     |              `column IS NULL`              |      |
|   `@IsNotNull`    |            `column IS NOT NULL`            |      |
|     `@Equal`      |            `column = #{value}`             |      |
|    `@NotEqual`    |            `column != #{value}`            |      |
|   `@GreatThan`    |            `column > #{value}`             |      |
| `@GreatThanEqual` |            `column >= #{value}`            |      |
|    `@LessThan`    |            `column < #{value}`             |      |
| `@LessThanEqual`  |            `column <= #{value}`            |      |
|       `@In`       |           `column IN (#{value})`           |      |
|     `@NotIn`      |         `column NOT IN (#{value})`         |      |
|      `@Like`      |           `column LIKE #{value}`           |      |
|    `@NotLike`     |         `column NOT LIKE #{value}`         |      |
|    `@Contains`    |   `column LIKE CONCAT('%',#{value},'%')`   |      |
|  `@NotContains`   | `column NOT LIKE CONCAT('%',#{value},'%')` |      |
|   `@StartWith`    |     `column LIKE CONCAT('%',#{value})`     |      |
|  `@NotStartWith`  |   `column NOT LIKE CONCAT('%',#{value})`   |      |
|    `@EndWith`     |     `column LIKE CONCAT(#{value},'%')`     |      |
|   `@NotEndWith`   |   `column NOT LIKE CONCAT(#{value},'%')`   |      |
|     `@Limit`      |              `LIMIT #{limit}`              |      |
|     `@Offset`     |         `LIMIT #{offset},#{limit}`         |      |
|                   |                                            |      |


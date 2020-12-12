---
layout: post title: query subtitle: query description: query tags: []
menus:

- annotation-query date: 2020-11-18 21:21:53 +800 version: 1.0
---

# Query

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

## AnnotationQuery

### AndQuery

当你需要定义如下的`sql`时：

```sql
WHERE  a = ? AND b BETWEEN ? AND ? AND c NOT IN (?,?,?)
```

定义如下的`Query`对象：

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

生成的`mapper`：

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

### OrQuery

​ 当需要使用`OR`对`sql`进行连接时：

```sql
WHERE  a = ? OR b BETWEEN ? AND ? OR c NOT IN (?,?,?)
```

定义如下的`Query`对象：

```java

@Data
@OR
static class OrQuery implements IQuery {

    @Equal
    private String a;

    @NotBetween
    private BetweenValue<String> b;

    @NotIn
    private List<String> c;

}
```

生成的`mapper`片段

```xml

<where>
    <if test="query.a != null">
        <![CDATA[ OR a = #{query.a}]]>
    </if>
    <if test="query.b != null and query.b.min != null and query.b.max != null">
        <![CDATA[ OR b BETWEEN #{query.b.min} AND #{query.b.max}]]>
    </if>
    <if test="query.c != null">
        <foreach collection="query.c" item="item" open=" OR c NOT IN (" close=")" separator=",">#{item}</foreach>
    </if>
</where>

```

### AndOrQuery

当需要先使用`AND`嵌套`OR`对`sql`进行连接时：

```sql
WHERE  a = ? AND (  b = ? OR c != ? )
```

定义如下`Query`对象：

```java

@Data
static class AndOrQuery implements IQuery {

    @Equal
    private String a;

    @OR
    private InnerQuery innerQuery;

}

@Data
static class InnerQuery {

    @Equal
    private String b;

    @NotEqual
    private String c;

}
```

生成的`mapper`片段：

```xml

<where>
    <if test="query.a != null">
        <![CDATA[ AND a = #{query.a}]]>
    </if>
    <if test="query.innerQuery != null">
        <trim prefix=" AND (" suffix=")" prefixOverrides="AND |OR ">
            <if test="query.innerQuery.b != null">
                <![CDATA[ OR b = #{query.innerQuery.b}]]>
            </if>
            <if test="query.innerQuery.c != null">
                <![CDATA[ OR c != #{query.innerQuery.c}]]>
            </if>
        </trim>
    </if>
</where>

```

### OrAndQuery

当需要先使用OR嵌套`AND`对`sql`进行连接时：

```sql
WHERE  a = ? OR (  b = ? AND c != ? )
```

定义如下`Query`对象：

```java

@Data
@OR
static class OrAndQuery implements IQuery {

    @Equal
    private String a;

    @Criteria
    private InnerQuery innerQuery;

}

@Data
static class InnerQuery {

    @Equal
    private String b;

    @NotEqual
    private String c;

}
```

生成的`mapper`片段

```xml

<where>
    <if test="query.a != null">
        <![CDATA[ OR a = #{query.a}]]>
    </if>
    <if test="query.innerQuery != null">
        <trim prefix=" OR (" suffix=")" prefixOverrides="AND |OR ">
            <if test="query.innerQuery.b != null">
                <![CDATA[ AND b = #{query.innerQuery.b}]]>
            </if>
            <if test="query.innerQuery.c != null">
                <![CDATA[ AND c != #{query.innerQuery.c}]]>
            </if>
        </trim>
    </if>
</where>

```

### QueryAnnotations

* Common Annotation

| Annotation | SQL | 备注 | | :---------------: | :----------------------------------------: | :--: | |     `@IsNull`
|              `column IS NULL`              | | |   `@IsNotNull`    |            `column IS NOT NULL`            | |
|     `@Equal`      |            `column = #{value}`             | | |    `@NotEqual`
|            `column != #{value}`            | | |   `@GreatThan`    |            `column > #{value}`             | |
| `@GreatThanEqual` |            `column >= #{value}`            | | |    `@LessThan`
|            `column < #{value}`             | | | `@LessThanEqual`  |            `column <= #{value}`            | |
|       `@In`       |           `column IN (#{value})`           | | |     `@NotIn`
|         `column NOT IN (#{value})`         | | |    `@Between`     |     `column BETWEEN #{min} AND #{max}`     | |
|   `@NotBetween`   |   `column NOT BETWEEN #{min} AND #{max}`   | | |      `@Like`
|           `column LIKE #{value}`           | | |    `@NotLike`     |         `column NOT LIKE #{value}`         | |
|    `@Contains`    |   `column LIKE CONCAT('%',#{value},'%')`   | | |  `@NotContains`
| `column NOT LIKE CONCAT('%',#{value},'%')` | | |   `@StartWith`    |     `column LIKE CONCAT('%',#{value})`     | |
|  `@NotStartWith`  |   `column NOT LIKE CONCAT('%',#{value})`   | | |    `@EndWith`
|     `column LIKE CONCAT(#{value},'%')`     | | |   `@NotEndWith`   |   `column NOT LIKE CONCAT(#{value},'%')`   | |
|     `@Limit`      |              `LIMIT #{limit}`              | | |     `@Offset`
|         `LIMIT #{offset},#{limit}`         | | | | | |

* Json Annotation

| Annotation | SQL | 备注 | | :-----------------: | :-----------------------------------------: | :--: |
|  `@JsonContaions`   | `JSON_CONTAINS( column, #{value}, {path})`  | | | `@NotJsonContaions`
| `!JSON_CONTAINS( column, #{value}, {path})` | | | | | | | | | |


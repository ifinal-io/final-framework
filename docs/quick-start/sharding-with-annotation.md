---
layout: post title: sharding-with-annotation subtitle: sharding-with-annotation description:
sharding-with-annotation tags: []
menus:

- sharding-with-annotation date: 2020-12-09 21:03:17 +800 version: 1.0

---

# sharding-with-annotation

## Define Sharding Table

在实体类`Entity`上声明`@ShardingTable`注解。

|       属性        |  数据类型  |                             说明                             | 默认值  |
| :---------------: | :--------: | :----------------------------------------------------------: | :-----: |
|   `logicTables`   | `String[]` |                        分片逻辑表名称                        |    -    |
| `actualDataNodes` | `String[]` | 由数据源名 + 表名组成，以小数点分隔。<br />多个表以逗号分隔，支持行表达式 |    -    |
|     `binding`     | `boolean`  |                          是否绑定表                          | `false` |
|    `broadcast`    | `boolean`  |                          是否广播表                          | `false` |

## Define Sharding Strategy

### Inline Sharding Strategy——行表达式分片算法

使用`@InlineShardingStrategy`注解声明行内（`INLINE`）分片（库）策略。使用 Groovy 的表达式，提供对 SQL 语句中的 `=` 和 `IN`
的分片操作支持，只支持单分片键。 对于简单的分片算法，可以通过简单的配置使用，从而避免繁琐的 Java 代码开发，如: `t_user_$->{u_id % 8}` 表示 `t_user`
表根据 `u_id` 模 8，而分成 8 张表，表名称为 `t_user_0`
到 `t_user_7`。

> 类型：**INLINE**

* 使用`@Annotation`声明式配置

```java

@Setter
@Getter
@ShardingTable(logicTables = {"person"}, actualDataNodes = "ds${0..1}.${logicTable}_0${0..1}")
@InlineShardingStrategy(scope = ShardingScope.DATABASE, columns = "age", expression = "ds${age % 2}")
@InlineShardingStrategy(scope = ShardingScope.TABLE, columns = "age", expression = "${logicTable}_0${age % 2}")
public class Person extends AbsEntity {

    private String name;

    private Integer age;

}
```

* `shardingsphere 4.+`

```yaml
shardingRule:
  tables:
    person:
      actualDataNodes: ds${0..1}.person_0${0..1}
      databaseStrategy:
        inline:
          shardingColumn: age
          algorithmExpression: ds${age % 2}
      tableStrategy:
        inline:
          shardingColumn: age
          algorithmExpression: person_0${age % 2}
```

* `shardingsphere 5.+`

```yaml
rules:
  # 配置分片规则
  - !SHARDING
    tables:
      # 配置 t_order 表规则
      person:
        actualDataNodes: ds${0..1}.person_0${0..1}
        # 配置分库策略
        databaseStrategy:
          standard:
            shardingColumn: age
            shardingAlgorithmName: Person-DATABASE-INLINE
        # 配置分表策略
        tableStrategy:
          standard:
            shardingColumn: age
            shardingAlgorithmName: Person-TABLE-INLINE
      t_order_item:
    # 配置分片算法
    shardingAlgorithms:
      Person-DATABASE-INLINE:
        type: INLINE
        props:
          algorithm-expression: ds${age % 2}
      Person-TABLE-INLINE:
        type: INLINE
        props:
          algorithm-expression: person_0${age % 2}
```

**可配置属性：**

|       属性        |  数据类型  |                            说明                            | 默认值  |
| :---------------: | :--------: | :--------------------------------------------------------: | :-----: |
|      `scope`       |  `ShardingScope`  |                        算法域（`DATABASE`或`TABLE`）                        |    -    |
|     `columns`     | `String[]` |                         分片列名称                         |    -    |
|   `expression`    |  `String`  |                     分片算法的行表达式                     |    -    |
| `allowRangeQuery` | `boolean`  | 是否允许范围查询。注意：范围查询会无视分片策略，进行全路由 | `false` |

### Interval Sharding Strategy——时间范围分片算法

| 属性 | 数据类型 | 说明 | 默认值 | | :--------: | :-------------: | :--: | :----: | |  `scope`
| `ShardingScope` | | | | `columns`  |   `String[]`    | | | | `pattern`  |    `String`     | | | |  `lower`
|    `String`     | | | |  `upper`   |    `String`     | | | |  `suffix`  |    `String`
| | | | `interval` |      `int`      | |  `1`   | |   `unit`   |  `ChronoUnit`   | | `DAYS` |


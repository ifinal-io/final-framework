---
layout: post
title: sharding-with-annotation 
subtitle: sharding-with-annotation 
description: sharding-with-annotation 
tags: [] 
menus: 
    - sharding-with-annotation 
date: 2020-12-09 21:03:17 +800 
version: 1.0
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



### Inline Sharding Strategy

使用`@TableInlineShardingStrategy`和`@DatabaseInlineShardingStrategy`注解声明行内（`INLINE`）分片（库）策略。



* 使用`@Annotation`声明式配置

```java
@Setter
@Getter
@ShardingTable(logicTables = {"person"},actualDataNodes = "ds${0..1}.${logicTable}_0${0..1}")
@DatabaseInlineShardingStrategy(columns = "age",expression = "ds${age % 2}")
@TableInlineShardingStrategy(columns = "age",expression = "${logicTable}_0${age % 2}")
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





|       属性        |  数据类型  |                            说明                            | 默认值  |
| :---------------: | :--------: | :--------------------------------------------------------: | :-----: |
|      `name`       |  `String`  |                        分片算法名称                        |    -    |
|     `columns`     | `String[]` |                         分片列名称                         |    -    |
|   `expression`    |  `String`  |                     分片算法的行表达式                     |    -    |
| `allowRangeQuery` | `boolean`  | 是否允许范围查询。注意：范围查询会无视分片策略，进行全路由 | `false` |


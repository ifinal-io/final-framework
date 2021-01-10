---
formatter: off
layout: post
title: 对象关系映射 
subtitle: orm 
description: orm 
tags: [orm] 
date: 2021-01-10 17:46:06 +800 
version: 1.0
formatter: on
---

# 对象关系映射

## Annotations

|   @Annotation   |                    说明                    |
| :-------------: | :----------------------------------------: |
|    `@Table`     |                  指定表名                  |
|    `@Column`    |                  指定列名                  |
|    `@Final`     |                指定不可修改                |
|   `@Default`    |                指定不需插入                |
|   `@Keyword`    |                   关键字                   |
|     `@Json`     |                    Json                    |
|  `@PrimaryKey`  |                    主键                    |
|   `@AutoInc`    |                    自增                    |
|   `@ReadOnly`   |                    只读                    |
|  `@Transient`   |                  非数据列                  |
|  `@WriteOnly`   |                    只写                    |
|   `@Virtual`    |                   虚拟列                   |
|   `@Version`    | 版本,更新时自动执行`version = version + 1` |
|   `@Created`    |         创建时间, @Final,@Default          |
|   `@Creator`    |                创建,@Final                 |
| `@LastModified` |                末次修改时间                |
| `@LastModifier` |                 末次修改人                 |
|                 |                                            |
|                 |                                            |
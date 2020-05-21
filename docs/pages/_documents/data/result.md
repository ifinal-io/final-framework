---
layout: post
title: Result
subtitle: 统一的结果集封装
description: 全局统一的结果集对象。
categories: []
tags: [data,result]
menus:
    - data
    - result
author: likly
date: 2019-09-24 11:27:46 +800
version: 1.0
---

# Result——统一的结果集对象

## What

[Result](/final-data/final-data-context/src/main/java/org/finalframework/data/result/Result.java)——全局统一的结果集对象。
它描述了一次业务的执行结果。

### Result

|   属性    |  类型   | 非空 |   含义   | 备注 |
| :-------: | :-----: | :--: | :------: | :--: |
|  `status`  | Integer |  Y   |  状态码  |      |
|  description  | String  |  Y   | 状态描述 |      |
|  code   | Integer |  Y   |  业务码  |      |
|  message  | String  |  Y   | 业务描述 |      |
|   `data`  |    T    |  N   |   数据   | 业务数据实体，可能是数据实体集 |
|   `page`  |    Page    |  N   |   分页信息   | 仅当分页查询时有效 |
|   `trace`   | String  |  Y   |   链路   | 链路追踪，方便日志查询 |
| duration |  Long   |  Y   |  执行时长  | 执行用时(ms) |
| timestamp |  Long   |  Y   |  时间戳  | 响应时间戳 |
| locale |  Locale   |  Y   |  地区  |  |
| timeZone |  Timezone   |  Y   |  时区  |  |
| operator |  IUser   |  N   |  操作人  |  |
| view |  Class  |  N  |  视图  | 结果视图 |
| `success` |  Boolean  |  Y   |  业务状态  | 是否成功 |


> `Result#isSuccess()`方法描述该业务是否执行成功，`true`表示成功，否则表示失败。

### Page

|   属性    |  类型   | 非空 |   含义   | 备注 |
| :-------: | :-----: | :--: | :------: | :--: |
|   page    | Integer |  Y   | 当前页码 |      |
|   size    | Integer |  Y   | 页面容量 |      |
|   total   | Long |  N   |  总条数  |      |
|   pages   | Integer |  N   |  总页数  |      |
| firstPage | Boolean |  Y   | 是否首页 |      |
| lastPage  | Boolean |  Y   | 是否尾页 |      |


## Usage

### Define RestController

```java
@RestController
@RequestMapping("/person")
public class PersonController {
    public static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Resource
    private PersonService personService;

    @GetMapping
    public List<Person> query(PageQuery query) {
        return personService.select(new Query().page(query.getPage(), query.getSize()));
    }
}
```

### Request the Url

```json
{
    "status": 0,
    "description": "success",
    "code": "0",
    "message": "success",
    "data": [
        {
            "id": 1,
            "created": 1589928637000,
            "createdFormat": "2020-05-20 06:50:37",
            "yn": 1,
            "ynName": "YES",
            "ynDesc": "有效",
            "ynDescription": "有效",
            "name": "xiaomin1",
            "age": 1
        },
        {
            "id": 2,
            "created": 1589939202000,
            "createdFormat": "2020-05-20 09:46:42",
            "yn": 1,
            "ynName": "YES",
            "ynDesc": "有效",
            "ynDescription": "有效",
            "name": "haha3",
            "age": 101,
            "stringList": [
                "aaa",
                "bbb"
            ],
            "intList": [
                1,
                2,
                3
            ],
            "properties": {
                "int": 1,
                "string": "String"
            }
        },
        {
            "id": 2,
            "created": 1590088701000,
            "createdFormat": "2020-05-22 03:18:21",
            "yn": 1,
            "ynName": "YES",
            "ynDesc": "有效",
            "ynDescription": "有效",
            "name": "2222",
            "age": 102
        }
    ],
    "page": {
        "page": 1,
        "size": 20,
        "pages": 1,
        "total": 3,
        "firstPage": true,
        "lastPage": true
    },
    "trace": "78bae3ec-5c36-4822-b11d-2a68fae4a251",
    "timestamp": 1590043632804,
    "duration": 61,
    "locale": "zh_CN",
    "timeZone": "Asia/Shanghai",
    "success": true
}
```



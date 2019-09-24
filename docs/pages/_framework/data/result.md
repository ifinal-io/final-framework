---
layout: post
title: Result
subtitle: 统一的结果集封装
description: 全局统一的结果集对象。
categories: []
tags: []
menus:
    - data
    - result
author: likly
date: 2019-09-24 11:27:46 +800
version: 1.0
---

# Result——统一的结果集对象

[Result](/final-data/final-data-core/src/main/java/org/finalframework/data/result/Result.java)——全局统一的结果集对象。
它描述了一次业务的执行结果。



## What

|   属性    |  类型   | 非空 |   含义   | 备注 |
| :-------: | :-----: | :--: | :------: | :--: |
|  status   | Integer |  Y   |  状态码  |      |
|  message  | String  |  Y   | 状态描述 |      |
|   data    |    T    |  N   |   数据   |      |
|   trace   | String  |  Y   |   链路   |      |
| timestamp |  Long   |  Y   |  时间戳  |      |



## Usage

```java
public T getRemoteData(Param param){
    Result<T> result = getRemoteResult(param);
    if(result.isSuccess(){
        return result.getData();
    }else{
        throw new ServiceException(result.getStatus(),result.getMessage());
    }
}
```

> `Result#isSuccess()`方法描述该业务是否执行成功，`true`表示成功，否则表示失败。


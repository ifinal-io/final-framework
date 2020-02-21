---
title: CacheLock
subtitle: 分布式缓存锁
description: 分布式同步怎么搞？这个就够了！
layout: post
categories: []
tags: []
menus: 
    - usage
    - cache-lock
author: likly
date: 2019-03-16 15:15:27 +800
version: 1.0
---

# CacheLock
## What

**[CacheLock](/final-cache/final-cache-core/src/main/java/org/finalframework/cache/annotation/CacheLock.java)**，基于注解(`Annotation`)
的分布式缓存锁实现，快速为业务方法添加分布式锁功能。

## Usage

在需要使用分布式锁的方法**Method**上声明`CacheLock`注解，并配置相应的`key`和`value`，以及过期时间`ttl`及重试次数`retry`和重试间隔`sleep`，
即可给目标方法**Method**增加分页式锁的功能。

**示例**：

* **需求**： 在订单服务`OrderService`的下单方法`order(request)`中，对于同一个下单请求`request`在同一时刻有且仅能有一个下单动作，避免造成用户重复下单的问题。
* **编程方式**:
```java
public class OrderServiceImpl implements OrderService{

    @Resource
    private Cache cache;

    @Override
    public Order order(Request request){
        
        final String key = "lock:" + request.id;
        final String value = UUID.randomUUID().toString();
        
        try{
            if(cache.lock(key,value,5,TimeUnit.MINUTES)){
                // do order
            }
            throw new ServiceExcetion(500,"订单正在处理...");
        }finally{
            cache.unlock(key,value);
        }
    }
}
```
* **注解方式**
```java
public interface OrderService{
    @CacheLock(key={"lock:{#request.id}"},ttl=5,timeunit=TimeUnit.MINUTES)
    Order order(Request request);
}
```


## Options

|   配置项    |    类型    | 是否可选 |     含义     |         默认值          |
| :---------: | :--------: | :------: | :----------: | :---------------------: |
|    `key`    |  String[]  |    N     |  缓存锁Key   |           无            |
|   `value`   |   String   |    Y     | 缓存锁Value  |         `UUID`          |
| `delimiter` |   String   |    Y     |    分隔符    |           `:`           |
| `condition` |   String   |    Y     |     条件     |           无            |
|    `ttl`    |    Long    |    Y     |   有效时间   |        -1,不过期        |
| `timeunit`  | `TimeUnit` |    Y     | 有效时间单位 | `TimeUnit.MILLISECONDS` |
|   `retry`   |    Int     |    Y     |   重试次数   |       `0，不重试`       |
|   `sleep`   |    Long    |    Y     |   重试间隔   |       `1000`(ms)        |


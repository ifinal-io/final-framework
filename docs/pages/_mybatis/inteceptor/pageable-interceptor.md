---
title: PageableInterceptor
subtitle: 分页拦截器
description: 分页拦截器，实现全局的分页功能。
layout: page
categories: []
tags: []
menus:
    - interceptor
    - pageable-interceptor
author: likly
date: 2019-03-24 23:31:22 +800
version: 1.0
---

# PageableInterceptor

## What

[PageableInterceptor](/final-mybatis/final-mybatis-core/src/main/java/org/finalframework/mybatis/inteceptor/PageableInterceptor.java)
分页拦截器插件实现在`Mapper`执行之前进行分页功能。

## How

`PageableInterceptor`分页拦截器的原理是当`MappedStatement`的参数（或参数列表中的某一个）实现了[Pageable](/final-data/final-data-context/src/main/java/org/finalframework/data/query/Pageable.java)
接口，并且执行的是`query`方法，那么该方法将会被执行分页拦截。是否分页取决于`Pageable#getPage()`和`Pageable#getSize()`的值都存在。


```java
public abstract class PageableInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        try {
            Object[] args = invocation.getArgs();
            MappedStatement ms = (MappedStatement) args[0];
            Object parameter = args[1];
            if (!ms.getId().contains("selectCount") && !ms.getId().contains("selectOne") && parameter != null) {
                if (parameter instanceof Map) {
                    Map map = (Map) parameter;
                    for (Object item : map.values()) {
                        if (item instanceof Pageable) {
                            startPage((Pageable) item);
                            return invocation.proceed();
                        }
                    }
                } else if (parameter instanceof Pageable) {
                    startPage((Pageable) parameter);
                    return invocation.proceed();
                }
            }
            return invocation.proceed();

        } catch (Exception e) {
            return invocation.proceed();
        }

    }

    protected abstract void startPage(Pageable pageable);
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
```

## Usage

1. 使用[Query](/final-data/final-data-context/src/main/java/org/finalframework/data/query/Query.java)查询；
2. 自定义查询参数，并实现[Pageable](/final-data/final-data-context/src/main/java/org/finalframework/data/query/Pageable.java)接口,
如[PageQuery](/final-data/final-data-context/src/main/java/org/finalframework/data/query/PageQuery.java)




## Impls

### PageHelperPageableInterceptor

```java
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
public class PageHelperPageableInterceptor extends PageableInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(PageHelperPageableInterceptor.class);

    @Override
    protected void startPage(Pageable pageable) {
        if (Assert.isNull(pageable) || Assert.isNull(pageable.getPage()) || Assert.isNull(pageable.getSize())) return;
        startPage(pageable.getPage(), pageable.getSize(), Boolean.TRUE.equals(pageable.getCount()), false, false);
    }

    /**
     * @see PageHelper#startPage(int, int, boolean, Boolean, Boolean)
     * @param pageNum      页码
     * @param pageSize     每页显示数量
     * @param count        是否进行count查询
     * @param reasonable   分页合理化,null时用默认配置
     * @param pageSizeZero true且pageSize=0时返回全部结果，false时分页,null时用默认配置
     */
    private void startPage(int pageNum, int pageSize, boolean count, Boolean reasonable, Boolean pageSizeZero) {
        final Page<Object> result = PageHelper.startPage(pageNum, pageSize, count, reasonable, pageSizeZero);
        logger.info("pageResult:page={},size={},pages={},total={}",
                result.getPageNum(), result.getPageSize(), result.getPages(), result.getTotal());
    }

}

```

> 目前使用的分页插件为[PageHelper](/final-mybatis/final-mybatis-core/src/main/java/org/finalframework/mybatis/inteceptor/PageHelperPageableInterceptor.java),
访问[GitHub](https://github.com/pagehelper/Mybatis-PageHelper/blob/master/README_zh.md)。
>
> 感谢作者[LiuZh](https://github.com/abel533)的贡献。
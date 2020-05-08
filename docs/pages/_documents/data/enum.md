---
layout: post
title: Enum
subtitle: 优雅的使用枚举
categories: [data]
tags: [enum]
menus:
    - data
    - enum
author: likly
date: 2020-03-22 20:46:57 +800
version: 1.0
---

# Enum

## 前言

在项目开发中，经常会使用到`Enum`来约束一些行为，或者限制数值的范围。
但是，这些`Enum`常量在`API`的提供中，又常常需要转换为其对应的`code`或`key`，持久化到数据库亦然。
因此，在项目中会出现大量的类型`Enum.getCode()`和`Enum.valueOf(code)`的代码片段。

那么，有没有有一种方式，能够实现`Enum`在 `API<->Project<->DB` 之间的优雅传递呢，即`API`传入或返回是`Enum`对应的`code`，
`Project`在接收时直接使用`Enum`接收，在持久化到`DB`时，又是`Enum`对应的`code`，反之亦然！

答案是肯定的！！！

## IEnum

[IEnum](/final-data/final-data-annotation/src/main/java/org/finalframework/data/annotation/IEnum.java) 是实现上述方式的一个桥梁，
项目中的`Enum`只要实现了该接口，即可看到`Enum`在 `在 `API<->Project<->DB` 之间进行着优雅的传递。

其定义如下：

```java
public interface IEnum<T> {

    /**
     * 返回表示该枚举常量的code
     */
    @JsonValue
    T getCode();

    /**
     * 返回对该枚举常量的描述信息
     */
    String getDescription();

}
```
## I18N

实现了`IEnum`接口的`Enum`类在返回前端的时候，会进行国际化处理，其国际化描述信息需要放在对应语言的`messages.perperties`中，
`key`的格式为：

```java
{Enum.class.getCanonicalName()}.{Enum.name().toLowerCase(Locale.ENGLISH)}
```

## YN
[YN](/final-data/final-data-annotation/src/main/java/org/finalframework/data/entity/enums/YN.java)表示`YES`和`NO`,
用于标记数据的有效性，其实现了`IEnum`接口。

定义如下：

```java

public enum YN implements IEnum<Integer> {
    /**
     * 有效
     */
    YES(1, "有效"),
    /**
     * 无效
     */
    NO(0, "无效"),
    /**
     * 删除
     */
    DELETED(-1, "删除");
    /**
     * 枚举码
     */
    private final Integer code;
    private static final Map<Integer, YN> cache = Arrays.stream(values()).collect(Collectors.toMap(YN::getCode, Function.identity()));

    private final String description;

    YN(Integer code, String description) {
        this.code = code;
        this.description = description;
    }


    @JsonCreator
    public static YN valueOf(int value) {
        return cache.get(value);
    }

    @Override
    @JsonValue
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
```

### I18N描述 

* messages.properties

```properties
org.finalframework.data.entity.enums.YN.yes=有效
org.finalframework.data.entity.enums.YN.no=无效
```


* messages_en_US.properties

```properties
org.finalframework.data.entity.enums.YN.yes=YES
org.finalframework.data.entity.enums.YN.no=NO
```
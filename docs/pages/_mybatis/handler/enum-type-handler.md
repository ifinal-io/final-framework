---
post: post
title: EnumTypeHandler
subtitle: 枚举类型处理器
layout: post
categories: []
tags: []
menus:
    - handler
    - enum-type-handler
author: likly
date: 2019-03-24 23:08:57 +800
version: 1.0
---

# EnumTypeHandler

## What

[EnumTypeHandler](/final-mybatis/final-mybatis-core/src/main/java/org/finalframework/mybatis/handler/EnumTypeHandler.java)
实现`Enum`类与数据库的映射关系，避免在程序在枚举与常量的相互转换。

## Usage

要实现`Enum`类与数据库的自动映射，只需要定义的枚举类实现[IEnum](/final-data/final-data-core/src/main/java/org/finalframework/data/entity/enums/IEnum.java)
接口，即可实现映射。

*示例*：

如[YN](/final-data/final-data-core/src/main/java/org/finalframework/data/entity/enums/YN.java)：

```java
public enum YN implements IEnum<Integer> {
    /**
     * 有效
     */
    YES(1),
    /**
     * 无效
     */
    NO(0);
    /**
     * 枚举码
     */
    private final Integer code;


    YN(Integer code) {
        this.code = code;
    }

    @JsonCreator
    public static YN valueOf(int value) {
        for (YN yn : values()) {
            if (yn.code.equals(value)) {
                return yn;
            }
        }
        return null;
    }

    @Override
    @JsonValue
    public Integer getCode() {
        return code;
    }
}
```

> `@JsonCreator`和`@JsonValue`可实现枚举与`Json`之间的映射，这样就可实现`json`和`database`中都使用`IEnum#getCode()`所返回的常量值，在程序中使用枚举。
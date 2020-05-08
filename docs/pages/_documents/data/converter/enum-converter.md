---
layout: post
title: EnumConverter
subtitle: 枚举转换器
categories: []
tags: []
menus:
    - data
    - converter
    - enum-converter
author: likly
date: 2020-05-08 18:32:32 +800
version: 1.0
---
# EnumConverter
[EnumConverter](/final-data/final-data-context/src/main/java/org/finalframework/data/converter/EnumConverter.java)
定义了`Json`序列化时`Enum`的转换接口，开发者可以根据业务需求自定义转换规则。

## IEnumConverter

[IEnumConverter](/final-data/final-data-context/src/main/java/org/finalframework/data/converter/IEnumConverter.java)
提供了默认的枚举转换规则，对于实现了[IEnum](../enum.md)的枚举，还支持`desc`属性的国际化。

开发者可以通过实现`EnumConverter`接口或继承`IEnumConverter`来扩展自定义的枚举转换器,并通过
[@EnumTarget](/final-data/final-data-context/src/main/java/org/finalframework/data/converter/EnumTarget.java)来指定扩展的枚举目标,
该实现需要注入到`Spring`容器中。

```java
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnumTarget(Enum.class)
public class IEnumConverter<T extends Enum<T>> implements EnumConverter<T> {

    /**
     * @see Enum#ordinal()
     */
    protected static final String ENUM_ORDINAL = "ordinal";
    /**
     * @see Enum#name()
     */
    protected static final String ENUM_NAME = "name";
    /**
     * @see IEnum#getCode()
     */
    protected static final String ENUM_CODE = "code";
    /**
     * @see IEnum#getDesc()
     */
    protected static final String ENUM_DESC = "desc";

    @Override
    public Map<String, Object> convert(T source) {
        final Map<String, Object> result = new HashMap<>();
        result.put(ENUM_NAME, source.name());
        result.put(ENUM_ORDINAL, source.ordinal());

        if (source instanceof IEnum) {
            result.put(ENUM_CODE, ((IEnum<?>) source).getCode());
            result.put(ENUM_DESC, Messages.getMessage(Enums.getEnumI18NCode(source), ((IEnum<?>) source).getDesc()));
        }
        return result;
    }
}

```
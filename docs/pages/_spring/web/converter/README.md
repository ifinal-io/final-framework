---
layout: post
title: Converter
subtitle: FATB（From A To B）的功能接口。
description: 转换器`Converter` 提供了统一的`FATB`的接口，实现
categories: [spring]
tags: [converter]
permalink: /spring/web/converter/index.html
menus:
    - web
    - converter
    - index
author: likly
date: 2019-11-11 15:25:04 +800
version: 1.0
---

# Converter


## Definition

```java
package org.springframework.core.convert.converter;

import org.springframework.lang.Nullable;

/**
 * A converter converts a source object of type {@code S} to a target of type {@code T}.
 *
 * <p>Implementations of this interface are thread-safe and can be shared.
 *
 * <p>Implementations may additionally implement {@link ConditionalConverter}.
 *
 * @author Keith Donald
 * @since 3.0
 * @param <S> the source type
 * @param <T> the target type
 */
@FunctionalInterface
public interface Converter<S, T> {

	/**
	 * Convert the source object of type {@code S} to target type {@code T}.
	 * @param source the source object to convert, which must be an instance of {@code S} (never {@code null})
	 * @return the converted object, which must be an instance of {@code T} (potentially {@code null})
	 * @throws IllegalArgumentException if the source cannot be converted to the desired target type
	 */
	@Nullable
	T convert(S source);

}

```

## Recommend

* [DateConverter](date-converter.md)：日期转换器，实现将一定格式的日期字符串转换为`Date`类型
* [EnumConverter](enum-converter.md)：枚举转换器，实现枚举值与枚举码之间的映射。
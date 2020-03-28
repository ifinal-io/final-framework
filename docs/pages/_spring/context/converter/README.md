---
layout: page
title: Converter
subtitle: CATB（Convert A To B）的功能接口。
description: 转换器`Converter` 提供了统一的`CATB`的接口，实现
categories: [spring]
tags: [converter]
permalink: /spring/context/converter/index.html
menus:
    - web
    - converter
    - index
author: likly
date: 2019-11-11 15:25:04 +800
version: 1.0
---

# Converter

## What

在项目开发中，经常会遇到需要`Convert A To B（CATB）`的需求，可以说这种需要占据工程中相当一部分的工作量。`Spring`也提供了相关的转换器接口，
以方便开发者快速实现这种需求，提供了简单的转换器`Converter`接口和转换器工厂`ConverterFactory`接口。

## Converter

### Definition

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

## Implements

* [DateConverter](date-converter.md)：日期转换器，实现将一定格式的日期字符串转换为`Date`类型
* [EnumConverter](enum-converter.md)：枚举转换器，实现枚举值与枚举码之间的映射。

## ConverterFactory

## Definition

```java

package org.springframework.core.convert.converter;

/**
 * A factory for "ranged" converters that can convert objects from S to subtypes of R.
 *
 * <p>Implementations may additionally implement {@link ConditionalConverter}.
 *
 * @author Keith Donald
 * @since 3.0
 * @param <S> the source type converters created by this factory can convert from
 * @param <R> the target range (or base) type converters created by this factory can convert to;
 * for example {@link Number} for a set of number subtypes.
 * @see ConditionalConverter
 */
public interface ConverterFactory<S, R> {

	/**
	 * Get the converter to convert from S to target type T, where T is also an instance of R.
	 * @param <T> the target type
	 * @param targetType the target type to convert to
	 * @return a converter from S to T
	 */
	<T extends R> Converter<S, T> getConverter(Class<T> targetType);

}

```
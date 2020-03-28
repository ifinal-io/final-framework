---
post: post
title: 对象序列化修改器
subtitle: 对对象的属性进行修饰或修改
layout: page
categories: []
tags: []
menus:
    - jackson
    - serializer
    - bean-property-serializer-modifier
author: likly
date: 2019-08-27 14:59:34 +800
version: 1.0
---

## BeanSerializerModifier




## BeanEnumPropertySerializerModifier

[BeanEnumPropertySerializerModifier](/final-json/final-json-jackson/src/main/java/org/finalframework/json/jackson/serializer/BeanEnumPropertySerializerModifier.java)
对`JavaBean`的枚举类型属性（实现了[IEnum](/final-data/final-data-context/src/main/java/org/finalframework/data/entity/enums/IEnum.java)接口）
进行了序列化修改，并其序列化为`IEnum#getCode()`所返回的值，并增加一个名为`xxxName`的属性来描述该枚举型属性的含义，其值为`IEnum#getDescription()`。
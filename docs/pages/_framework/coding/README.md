---
layout: post
title: README.md
subtitle: README.md
permalink: /framework/coding/index.html
categories: []
tags: []
menus:
    - coding
    - index
author: likly
date: 2019-11-04 11:05:29 +800
version: 1.0
---

# Coding

## Model

```
|-- AnnotatedConstruct
  |-- Element
  |  |-- AnnotatedConstruct
```

## Element

### PackageElement

> Represents a package program element.  Provides access to information about the package and its members.

### TypeElement

> Represents a class or interface program element.  Provides access to information about the type and its members.
> Note that an enum type is a kind of class and an annotation type is a kind of interface.

[DeclaredType](#DeclaredType)

### VariableElement

代表一个 `Field`,`enum`常量，`Method`或者

> Represents a field, {@code enum} constant, method or constructor parameter, local variable, resource variable, or exception parameter.

### ExecutableElement

 Represents a method, constructor, or initializer (static or instance) of a class or interface, including annotation type elements.

## TypeMirror

### ReferenceType

> Represents a reference type.
> These include class and interface types, array types, type variables, and the null type.

### DeclaredType

Represents a declared type, either a class type or an interface type.
This includes parameterized types such as {@code java.util.Set<String>} as well as raw types.
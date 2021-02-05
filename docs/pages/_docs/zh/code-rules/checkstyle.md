---
formatter: "@formatter:off"
layout: post 
title: checkstyle 
subtitle: checkstyle 
description: checkstyle 
tags: []
date: 2020-12-11 20:44:43 +800 
version: 1.0
formatter: "@formatter:on"
---

# checkstyle

## Imports

* 禁止 `improt *`

```xml

<module name="TreeWalker">

    <!-- Imports -->
    <!-- 禁止 import *-->
    <module name="AvoidStarImport"/>

</module>
```

## Names

```xml

<module name="TreeWalker">

    <!-- Names -->
    <module name="PackageName">
        <property name="format" value="^[a-z]+(\.[a-z][a-z0-9]*)*$"/>
        <message key="name.invalidPattern"
            value="Package name ''{0}'' must match pattern ''{1}''."/>
    </module>
    <module name="TypeName">
        <property name="tokens" value="CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF"/>
        <message key="name.invalidPattern" value="Type name ''{0}'' must match pattern ''{1}''."/>
    </module>
    <module name="MemberName">
        <property name="format" value="^[a-z][a-z0-9][a-zA-Z0-9]*$"/>
        <message key="name.invalidPattern" value="Member name ''{0}'' must match pattern ''{1}''."/>
    </module>
    <module name="ParameterName">
        <property name="format" value="^[a-z]([a-z0-9][a-zA-Z0-9]*)?$"/>
        <message key="name.invalidPattern"
            value="Parameter name ''{0}'' must match pattern ''{1}''."/>
    </module>
    <module name="LambdaParameterName">
        <property name="format" value="^[a-z]([a-z0-9][a-zA-Z0-9]*)?$"/>
        <message key="name.invalidPattern"
            value="Lambda parameter name ''{0}'' must match pattern ''{1}''."/>
    </module>
    <module name="CatchParameterName">
        <property name="format" value="^[a-z]([a-z0-9][a-zA-Z0-9]*)?$"/>
        <message key="name.invalidPattern"
            value="Catch parameter name ''{0}'' must match pattern ''{1}''."/>
    </module>
    <module name="LocalVariableName">
        <property name="format" value="^[a-z]([a-z0-9][a-zA-Z0-9]*)?$"/>
        <message key="name.invalidPattern"
            value="Local variable name ''{0}'' must match pattern ''{1}''."/>
    </module>
    <module name="ClassTypeParameterName">
        <property name="format" value="(^[A-Z][0-9]?)$|([A-Z][a-zA-Z0-9]*[T]$)"/>
        <message key="name.invalidPattern"
            value="Class type name ''{0}'' must match pattern ''{1}''."/>
    </module>
    <module name="MethodTypeParameterName">
        <property name="format" value="(^[A-Z][0-9]?)$|([A-Z][a-zA-Z0-9]*[T]$)"/>
        <message key="name.invalidPattern"
            value="Method type name ''{0}'' must match pattern ''{1}''."/>
    </module>
    <module name="InterfaceTypeParameterName">
        <property name="format" value="(^[A-Z][0-9]?)$|([A-Z][a-zA-Z0-9]*[T]$)"/>
        <message key="name.invalidPattern"
            value="Interface type name ''{0}'' must match pattern ''{1}''."/>
    </module>
</module>
```
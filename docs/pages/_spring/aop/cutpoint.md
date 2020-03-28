---
post: post
title: CutPoint
subtitle: 切点
layout: page
categories: [spring]
tags: [aop]
menus:
    - aop
    - cutpoint
author: likly
date: 2019-03-24 13:09:18 +800
version: 1.0
---

# CutPoint

## What

[CutPoint](/final-spring/final-spring-aop/src/main/java/org/finalframework/spring/aop/annotation/CutPoint.java) 
描述`AOP`的切入时机，可在方法执行前`Before`切入、也可在方法执行后`After`切入，`After`又细分为执行成功`AfterReturning`和执行失败`AfterThrowing`两种。

## CutPoints

<div  class="table-responsive">
<table>
    <thead>
    <tr>
        <th>CutPoint</th>
        <th>含义</th>
        <th>说明</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="text-rose">Before</td>
        <td>在方法之前</td>
        <td></td>
    </tr>
    <tr>
        <td class="text-rose">AfterReturning</td>
        <td>在方法执行成功之后</td>
        <td></td>
    </tr>
    <tr>
        <td class="text-rose">AfterThrowing</td>
        <td>在方法执行失败之后</td>
        <td></td>
    </tr>
    <tr>
        <td class="text-rose">After</td>
        <td>在方法之后</td>
        <td>包含<span class="text-rose">AfterReturning</span>和<span class="text-rose">AfterThrowing</span></td>
    </tr>
    </tbody>
</table>
</div>

---
post: post
title: CutPoint
subtitle: 切点
layout: post
categories: []
tags: []
menus:
    - aop
    - cutpoint
author: likly
date: 2019-03-24 13:09:18 +800
version: 1.0
---

# CutPoint

如同 `try~catch-finally` 代码块一样，`AOP`的切入点


<div  class="table-responsive">
<table>
    <thead>
    <tr>
        <th>Order</th>
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
        <td class="text-rose">After</td>
        <td>在方法之后</td>
        <td>包含<span class="text-rose">AfterReturning</span>和<span class="text-rose">AfterThrowing</span></td>
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
    </tbody>
</table>
</div>

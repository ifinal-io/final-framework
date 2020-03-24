---
layout: post
title: Replace
subtitle: Replace
categories: [data]
tags: [repository,replace]
menus:
    - data
    - repository
    - insert
author: likly
date: 2020-03-23 09:22:38 +800
version: 1.0
---

#  Replace
  
  `replace` 与 `insert` 功能雷同，但又有一丝区别，`insert` 当唯一约束存在时，重复数据插入会出错或者不插入（`INSERT IGNORE`）,
  而`replace`会先`delete`原有的数据，再`insert`新的数据，因此当`key`重复时，`replace`影响的行数为**2**.
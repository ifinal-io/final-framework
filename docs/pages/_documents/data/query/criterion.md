---
layout: post
title: Criterion
subtitle: 丰富的查询准则
categories: [data,query]
tags: [criterion]
menus:
    - data
    - query
    - criterion
author: likly
date: 2020-03-23 09:22:38 +800
version: 1.0
---

# Criterion

`Criterion`定义了最基本的查询`规则`。

## Null

* `isNull()`: 判断是否为空

```java
QProperty.isNull();
```
* `isNotNull()`: 判断值是否非空

```java
QProperty.isNotNull();
```

## Compare

### Equal

* `eq(value)`：等于

```java
QProperty.eq(value);
```

* `neq(value)`：不等于

```java
QProperty.neq(value);
```

### Great Than

* `gt(value)`：大于

```java
QProperty.gt(value);
```


* `gte(value)`：大于等于

```java
QProperty.gte(value);
```

### Less Than

* `lt(value)`：小于

```java
QProperty.lt(value);
```


* `lte(value)`：小于等于

```java
QProperty.lte(value);
```



## Between

* `between(min,max)`：在...之间

```java
QProperty.between(min,max);
```


* `notBetween(min,max)`：不在...之间

```java
QProperty.notBetween(min,max);
```

## Like

### Contains

* `contains(value)`：包含

```java
QProperty.contains(value);
```


* `notContains(value)`：不包含

```java
QProperty.notContains(value);
```

### StartWith

* `startWith(value)`：以...开始

```java
QProperty.contains(value);
```


* `notStartWith(value)`：不以...开始

```java
QProperty.notContains(value);
```

### EndWith

* `endWith(value)`：以...结束

```java
QProperty.contains(value);
```


* `notEndWith(value)`：不以...结束

```java
QProperty.notContains(value);
```

### Like

* `like(prefix,value,suffix)`：像

```java
QProperty.like(prefix,value,suffix);
```


* `notLike(prefix,value,suffix)`：不像

```java
QProperty.notLike(prefix,value,suffix);
```



## Json

[JsonCondition](/final-data/final-data-context/src/main/java/org/finalframework/data/query/condition/JsonCondition.java)
定义了基本的`Json`条件，如`JSON_CONTAINS(doc,val [,path])`。

### JSON_CONTAINS

* `jsonContains(value)`

```java
QProperty.jsonContains(value);
```

* `jsonContains(value,path)`

```java
QProperty.jsonContains(value,path);
```

* `notJsonContains(value)`

```java
QProperty.notJsonContains(value);
```


* `notJsonContains(value,path)`

```java
QProperty.notJsonContains(value,path);
```



{% include data/criterion.html %}
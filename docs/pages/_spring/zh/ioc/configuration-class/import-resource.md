---
formatter: "@formatter:off"
title: '@ImportResource'
subtitle: import-resource 
summary: import-resource 
tags: [] 
date: 2021-03-02 12:10:49 +800 
version: 1.0
formatter: "@formatter:on"
---

# @ImportResource

## What

**`@ImportResource`用于包含加载一个或多个`BeanDefinition`定义的资源。**

## Definition

```java
package org.springframework.context.annotation;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ImportResource {

    @AliasFor("locations")
    String[] value() default {};

    @AliasFor("value")
    String[] locations() default {};

    Class<? extends BeanDefinitionReader> reader() default BeanDefinitionReader.class;

}
```

|    属性     |    类型    |         说明         |            默认值            |
| :---------: | :--------: | :------------------: | :--------------------------: |
|   `value`   | `String[]` |       资源路径       |             `{}`             |
| `locations` | `String[]` |       资源路径       |             `{}`             |
|  `reader`   |  `Class`   | BeanDefinitionReader | `BeanDefinitionReader.class` |

## How

### Process

在`ConfigurationClassParser`的`doProcessConfigurationClass()`方法中，有如下的一段代码：

```java
package org.springframework.context.annotation;

class ConfigurationClassParser {

    protected final SourceClass doProcessConfigurationClass(
        ConfigurationClass configClass, SourceClass sourceClass, Predicate<String> filter)
        throws IOException {
        ...
        // Process any @ImportResource annotations
        AnnotationAttributes importResource =
            AnnotationConfigUtils.attributesFor(sourceClass.getMetadata(), ImportResource.class);
        if (importResource != null) {
            String[] resources = importResource.getStringArray("locations");
            Class<? extends BeanDefinitionReader> readerClass = importResource.getClass("reader");
            for (String resource : resources) {
                String resolvedResource = this.environment.resolveRequiredPlaceholders(resource);
                configClass.addImportedResource(resolvedResource, readerClass);
            }
        }
        ...
        return null;
    }

}
```

该代码片段实现将类上的`@ImportResource`注解声明的`locations`和`reader`存储到`ConfigurationClass`类的`importedResources`属性上。

### Load


---
formatter: "@formatter:off"
title: "@ImportResource"
subtitle: import-resource 
summary: import-resource 
tags: [spring,annotation] 
date: 2021-01-24 00:44:40 +800 
version: 1.0
formatter: "@formatter:on"
---

# ImportResource

## What

`ImportResource`用于指示导入一个或多个包含`BeanDefiniton`的资源。

## Usage

在资源类上声明`@ImportResource`注解并指定资源表达式。如`classpath`目录及其`config`、`spring`子目录下所有的`spring-config-*.xml`文件：

```java
package org.ifinal.finalframework;

@ImportResource({
    FinalFramework.CLASS_PATH_SPRING_CONFIG_XML,
    FinalFramework.CLASS_PATH_CONFIG_SPRING_CONFIG_XML,
    FinalFramework.CLASS_PATH_SPRING_SPRING_CONFIG_XML
})
public class FinalFramework {

    static final String CLASS_PATH_SPRING_CONFIG_XML = "classpath:spring-config-*.xml";

    static final String CLASS_PATH_CONFIG_SPRING_CONFIG_XML = "classpath*:config/spring-config-*.xml";

    static final String CLASS_PATH_SPRING_SPRING_CONFIG_XML = "classpath*:spring/spring-config-*.xml";

}

```

> 也可以指定自定义的`BeanDefinitionReader`。

## How

### Process

在`ConfigurationClassParser`类的`doProcessConfigurationClass()`方法中，有如下的代码片段：

```java
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
```

上述代码将`@ImportResource`注解中的`locations`和`reader`属性值添加到`BeanDefinition`中。

### Load

在`ConfigurationClassBeanDefinitionReader`类的`loadBeanDefinitionsFromImportedResources()`方法中实现资源的加载。

首先，通过资源文件的类型来实例化`BeanDefinitionReader`，如以`.groovy`结尾的资源使用`GroovyBeanDefinitionReader`
，其它资源使用`XmlBeanDefinitionReader`。

然后调用`BeanDefinitionReader`的`loadBeanDefinitions`方法来加载`BeanDefinition`。

```java
private void loadBeanDefinitionsFromImportedResources(
        Map<String, Class<? extends BeanDefinitionReader>> importedResources) {

    Map<Class<?>, BeanDefinitionReader> readerInstanceCache = new HashMap<>();

    importedResources.forEach((resource, readerClass) -> {
        // Default reader selection necessary?
        if (BeanDefinitionReader.class == readerClass) {
            if (StringUtils.endsWithIgnoreCase(resource, ".groovy")) {
                // When clearly asking for Groovy, that's what they'll get...
                readerClass = GroovyBeanDefinitionReader.class;
            }
            else {
                // Primarily ".xml" files but for any other extension as well
                readerClass = XmlBeanDefinitionReader.class;
            }
        }

        BeanDefinitionReader reader = readerInstanceCache.get(readerClass);
        if (reader == null) {
            try {
                // Instantiate the specified BeanDefinitionReader
                reader = readerClass.getConstructor(BeanDefinitionRegistry.class).newInstance(this.registry);
                // Delegate the current ResourceLoader to it if possible
                if (reader instanceof AbstractBeanDefinitionReader) {
                    AbstractBeanDefinitionReader abdr = ((AbstractBeanDefinitionReader) reader);
                    abdr.setResourceLoader(this.resourceLoader);
                    abdr.setEnvironment(this.environment);
                }
                readerInstanceCache.put(readerClass, reader);
            }
            catch (Throwable ex) {
                throw new IllegalStateException(
                        "Could not instantiate BeanDefinitionReader class [" + readerClass.getName() + "]");
            }
        }

        // TODO SPR-6310: qualify relative path locations as done in AbstractContextLoader.modifyLocations
        reader.loadBeanDefinitions(resource);
    });
}
```


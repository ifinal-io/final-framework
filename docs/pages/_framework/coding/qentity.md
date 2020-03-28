---
layout: page
title: QEntity
subtitle: QEntity
categories: []
tags: []
menus:
    - coding
    - qentity
author: likly
date: 2019-10-29 10:10:40 +800
version: 1.0
---

# QEntity

## Usage

### Maven

* Dependency

```xml
 <dependency>
    <groupId>org.finalframework</groupId>
    <artifactId>final-coding-query</artifactId>
    <version>{{site.final.version}}</version>
</dependency>
```

* Plugins

```xml
<plugin>
    <artifactId>maven-resources-plugin</artifactId>
    <version>2.5</version>
    <executions>
        <execution>
            <id>copy-files</id>
            <phase>process-sources</phase>
            <goals>
                <goal>copy-resources</goal>
            </goals>
            <configuration>
                <outputDirectory>${basedir}/target/classes</outputDirectory>
                <resources>
                    <resource>
                        <directory>${basedir}/target/maven-shared-archive-recources</directory>
                        <includes>
                            <include>{{site.final.entities}}</include>
                        </includes>
                    </resource>
                </resources>
            </configuration>
        </execution>
    </executions>
</plugin>
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-remote-resources-plugin</artifactId>
    <version>1.6.0</version>
    <configuration>
        <resourceBundles>
            <resourceBundle>{$YOUR_ENTITY_GROUP}:{$YOUR_ENTITY_ARTIFACT}:{$YOURE_PROJECT_VERSION}</resourceBundle>
        </resourceBundles>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>process</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## Model

```java

@Template("query/entity.vm")
public class QEntity {
    private final String packageName;
    private final String name;
    private final Entity entity;
    private final List<QProperty> properties;
    private final QProperty idProperty;
}
```

## Template


```
#set($package = $qEntity.package)
#set($entity = $qEntity.entity)
#set($name = $qEntity.name)
#set($properties = $qEntity.properties)
package ${package};

import org.finalframework.data.annotation.enums.PersistentType;
import org.finalframework.data.query.QProperty;
import org.finalframework.data.query.AbsQEntity;
import ${entity.package}.${entity.simpleName};

import javax.annotation.Generated;

#parse("/template/parse/header.vm")
@Generated("org.finalframework.coding.EntityProcessor")
public final class ${name} extends AbsQEntity<${entity.idProperty.type.replace("java.lang.","")},${entity.simpleName}>{

    public ${name}() {
        super(${entity.simpleName}.class);
    }

    public ${name}(String table) {
        super(${entity.simpleName}.class, table);
    }

    public static final ${name} ${entity.simpleName} = new ${name}();

#foreach($property in $properties)
    public static final QProperty<${property.type.replace("java.lang.","")}> ${property.name} = ${entity.simpleName}.getRequiredProperty("${property.path}");
#end

    @Override
    protected void initProperties() {
        super.initProperties();

#foreach($property in $properties)
        addProperty(
                QProperty.builder(this,${property.type.replace("java.lang.","")}.class)
                        .path("${property.path}").name("${property.name}").column("${property.column}")
                        .idProperty(${property.idProperty}).persistentType(PersistentType.${property.persistentType})
                        .insertable(${property.insertable}).updatable(${property.updatable}).selectable(${property.selectable})
                        .build()
        );
#end

    }


}

```
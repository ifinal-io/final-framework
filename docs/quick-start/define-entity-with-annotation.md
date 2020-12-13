n--- layout: post title: how-to-define-an-entity subtitle: how-to-define-an-entity description:
how-to-define-an-entity tags: []
menus:

- how-to-define-an-entity date: 2020-11-27 12:54:17 +800 version: 1.0

---

# How to define entity

## What

`Entity`通常代表数据实体，在`final-framework`的世界里，它表示与数据库表的映射关系。

## How

### Import Dependency

* maven

```xml
    <dependencies>
        <dependency>
            <groupId>org.ifinal.finalframework.boot</groupId>
            <artifactId>final-boot-starter-entity</artifactId>
        </dependency>
    </dependencies>
```

### Define Entity Bean

定义一个实现了`IEntity`接口的`Bean`。

```java
import lombok.Data;

@Data
public class Person implements IEntity<Long>{
    @AutoIncr
    @PrimaryKey
    private Long id;
    private String name;
    private Integer age;
}
```

### Declare @Table Annotation (Optional)

当实体名称(`Class.getSimpleName()`)与与数据库表名(`Table`)不相同时，可使用`@Table("table")`注解显示声明。

如：`Person`->`t_person`

```java
import lombok.Data;

@Data
@Table("t_person")
public class Person implements IEntity<Long>{
}
```

### Declare @Column Annotation (Optional)

当实体属性(`Field`)名称与数据表列名(`Column`)不相同时，可使用`@Column("column")`注解显示声明。

如：`name` -> `my_name`

```java
import lombok.Data;

@Data
public class Person implements IEntity<Long>{
    @Column("my_name")
    private String name;
}
```

### Declare @Json Annotation (Optional)

当实体属性(`Field`)需要以`JSON`格式存在到数据库中时，可使用`@Json`注解显示声明

如：`List<String> tags` -> `["tag"]`

```java
import lombok.Data;

@Data
public class Person implements IEntity<Long>{
    @Json
    private List<String> tags;
}
```

### Declare @Transient Annotation (Optional)

当实体属性(`Field`)不参与数据库列(`Column`)映射时，可使用`@Transient`注解显示声明。

```java
import lombok.Data;

@Data
public class Person implements IEntity<Long>{
    @Transient
    private String notColumn;
}
```

## Annotations

|   @Annotation   |                    说明                    |
| :-------------: | :----------------------------------------: |
|    `@Table`     |                  指定表名                  |
|    `@Column`    |                  指定列名                  |
|    `@Final`     |                指定不可修改                |
|   `@Default`    |                指定不需插入                |
|   `@Keyword`    |                   关键字                   |
|     `@Json`     |                    Json                    |
|  `@PrimaryKey`  |                    主键                    |
|   `@AutoInc`    |                    自增                    |
|   `@ReadOnly`   |                    只读                    |
|  `@Transient`   |                  非数据列                  |
|  `@WriteOnly`   |                    只写                    |
|   `@Virtual`    |                   虚拟列                   |
|   `@Version`    | 版本,更新时自动执行`version = version + 1` |
|   `@Created`    |         创建时间, @Final,@Default          |
|   `@Creator`    |                创建,@Final                 |
| `@LastModified` |                末次修改时间                |
| `@LastModifier` |                 末次修改人                 |
|                 |                                            |
|                 |                                            |

## See Also

* [如何使用CRUD](how-to-use-crud.md)
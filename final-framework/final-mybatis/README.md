---
layout: post title: README subtitle: README description: README tags: []
menus:

- README date: 2020-11-11 10:39:53 +800 version: 1.0

---

# final mybatis

`final mybaits` 基于`mybatis`封装，定义了实现常用`CURD`的通用`AbsMapper`。

## Create

`create`提供了多参数、多场景的原型方法定义，如`insert`，`replace`，`save`等，并提供了其多种重载方法，实现了多方位多场景的数据创建功能。

```java
public interface AbsMapper<ID extends Serializable, T extends IEntity<ID>> extends
    Repository<ID, T> {

    /**
     * @param table 表名
     * @param view 视图,
     * @param ignore 是否忽略重复数据,{@literal INSERT IGNORE}
     * @param entities 实体集
     * @see InsertSqlProvider#insert(ProviderContext, Map)
     */
    @Override
    @Options(useGeneratedKeys = true, keyProperty = "list.id", keyColumn = "id")
    @InsertProvider(InsertSqlProvider.class)
    int insert(@Param("table") String table, @Param("view") Class<?> view,
        @Param("ignore") boolean ignore,
        @Param("list") Collection<T> entities);

    /**
     * @param table 表名
     * @param view 视图
     * @param entities 实体集
     * @see InsertSqlProvider#replace(ProviderContext, Map)
     */
    @Override
    @Options(useGeneratedKeys = true, keyProperty = "list.id", keyColumn = "id")
    @InsertProvider(InsertSqlProvider.class)
    int replace(@Param("table") String table, @Param("view") Class<?> view,
        @Param("list") Collection<T> entities);

    /**
     * @param table 表名
     * @param view 视图
     * @param entities 实体集
     * @see InsertSqlProvider#save(ProviderContext, Map)
     */
    @Override
    @Options(useGeneratedKeys = true, keyProperty = "list.id", keyColumn = "id")
    @InsertProvider(InsertSqlProvider.class)
    int save(@Param("table") String table, @Param("view") Class<?> view,
        @Param("list") Collection<T> entities);

}
```

其`mapper.xml`原型如下：

```xml

<script>
    <trim prefix="INSERT INFO | INSERT IGNORE INTO | REPLACE INTO">
        ${table}
    </trim>
    <trim prefix="(" suffix=")" suffixOverrides=",">
        <if test="entity.property.hasView(view)">
            entity.property.column,
        </if>
    </trim>
    <foreach collection="list" item="entity" open="VALUES" separator=",">
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <if test="entity.property.hasView(view)">
                ${property.path,javaType=,typeHandler=},
            </if>
        </trim>
    </foreach>
    <trim prefix="ON DUPLICATE KEY UPDATE">
        column = values(column),
        version = version + 1,
        last_modified = NOW()
    </trim>
</script>
```




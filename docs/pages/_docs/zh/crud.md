---
layout: post title: repository subtitle: repository description: repository tags: []
menus:

- repository date: 2020-11-14 23:15:14 +800 version: 1.0

---

# repository

## insert

```java
public interface Repository<ID extends Serializable, T extends IEntity<ID>> {

    /**
     * 批量插入数据并返回影响的行数
     *
     * @param table    表名
     * @param view     视图,
     * @param ignore   是否忽略重复数据,{@literal INSERT IGNORE}
     * @param entities 实体集
     * @return 指插入数据所影响的行数
     */
    int insert(@Nullable String table, @Nullable Class<?> view, boolean ignore,
        @NonNull Collection<T> entities);

}
```

## replace

## save

## update

## delete

## select

## selectOne

## selectIds

## selectCount

`selectCount()`方法用于统计符合查询条件的记录数量，支持通过`ids`或`query`进行查询。

```java
public interface Repository<ID extends Serializable, T extends IEntity<ID>> {

    /**
     * 返回符合查询条件 {@link IQuery}的结果集的大小
     *
     * @param table 表名
     * @param query query
     * @return 符合查询条件 {@link IQuery}的结果集的大小
     */
    long selectCount(@Nullable String table, @Nullable Collection<ID> ids, @Nullable IQuery query);

}
```
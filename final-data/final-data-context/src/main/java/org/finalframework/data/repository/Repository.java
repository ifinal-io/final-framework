package org.finalframework.data.repository;

import org.apache.ibatis.annotations.Param;
import org.finalframework.core.Assert;
import org.finalframework.data.entity.IEntity;
import org.finalframework.data.query.Query;
import org.finalframework.data.query.Queryable;
import org.finalframework.data.query.Update;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-12 13:27
 * @since 1.0
 */
@SuppressWarnings({"unused", "unchecked"})
public interface Repository<ID extends Serializable, T extends IEntity<ID>> {

    /*=========================================== INSERT ===========================================*/
    default int insert(T... entities) {
        return insert(Arrays.asList(entities));
    }

    default int insert(Collection<T> entities) {
        return insert(null, null, entities);
    }

    default int insert(String tableName, T... entities) {
        return insert(tableName, Arrays.asList(entities));
    }

    default int insert(String tableName, Collection<T> entities) {
        return insert(tableName, null, entities, null);
    }

    default int insert(Class<?> view, T... entities) {
        return insert(view, Arrays.asList(entities));
    }

    default int insert(Class<?> view, Collection<T> entities) {
        return insert(null, view, entities);
    }

    default int insert(String tableName, Class<?> view, T... entities) {
        return insert(tableName, view, Arrays.asList(entities));
    }

    default int insert(String tableName, Class<?> view, Collection<T> entities) {
        return insert(tableName, view, entities, null);
    }

    default int insert(Collection<T> entities, Queryable query) {
        return insert(entities, query.convert());
    }

    default int insert(Collection<T> entities, Query query) {
        return insert(null, null, entities, query);
    }

    default int insert(String tableName, Collection<T> entities, Queryable query) {
        return insert(tableName, entities, query.convert());
    }

    default int insert(String tableName, Collection<T> entities, Query query) {
        return insert(tableName, null, entities, query);
    }

    default int insert(Class<?> view, Collection<T> entities, Queryable query) {
        return insert(view, entities, query.convert());
    }

    default int insert(Class<?> view, Collection<T> entities, Query query) {
        return insert(null, view, entities, query);
    }

    /**
     * 批量插入数据并返回影响的行数
     *
     * @param tableName 表名
     * @param view      视图
     * @param entities  实体集
     * @param query     条件
     * @return 指插入数据所影响的行数
     */
    @Deprecated
    int insert(@Param("tableName") String tableName, @Param("view") Class<?> view, @Param("list") Collection<T> entities, @Param("query") Query query);

    /*=========================================== UPDATE ===========================================*/

    default int update(T entity) {
        return update((String) null, entity);
    }

    default int update(String tableName, T entity) {
        return update(tableName, null, entity);
    }

    default int update(Class<?> view, T entity) {
        return update(null, view, entity);
    }

    default int update(T entity, boolean selective) {
        return update((String) null, entity, selective);
    }

    default int update(T entity, ID... ids) {
        return update(entity, Arrays.asList(ids));
    }

    default int update(T entity, Collection<ID> ids) {
        return update((String) null, entity, ids);
    }

    default int update(T entity, Queryable query) {
        return update(entity, query.convert());
    }

    default int update(T entity, Query query) {
        return update((String) null, entity, query);
    }

    default int update(T entity, boolean selective, ID... ids) {
        return update(entity, selective, Arrays.asList(ids));
    }

    default int update(T entity, boolean selective, Collection<ID> ids) {
        return update(null, null, entity, selective, ids);
    }

    default int update(T entity, boolean selective, Queryable query) {
        return update(entity, selective, query.convert());
    }

    default int update(T entity, boolean selective, Query query) {
        return update(null, null, entity, selective, query);
    }

    default int update(String tableName, Class<?> view, T entity) {
        return update(tableName, view, entity, true);
    }

    default int update(String tableName, T entity, boolean selective) {
        return update(tableName, null, entity, selective);
    }

    default int update(String tableName, T entity, ID... ids) {
        return update(tableName, entity, Arrays.asList(ids));
    }

    default int update(String tableName, T entity, Collection<ID> ids) {
        return update(tableName, null, entity, true, ids);
    }

    default int update(String tableName, T entity, Queryable query) {
        return update(tableName, entity, query.convert());
    }

    default int update(String tableName, T entity, Query query) {
        return update(tableName, null, entity, query);
    }

    default int update(Class<?> view, T entity, boolean selective) {
        return update(null, view, entity, selective);
    }

    default int update(Class<?> view, T entity, ID... ids) {
        return update(view, entity, Arrays.asList(ids));
    }

    default int update(Class<?> view, T entity, Collection<ID> ids) {
        return update(null, view, entity, ids);
    }

    default int update(Class<?> view, T entity, Queryable query) {
        return update(view, entity, query.convert());
    }

    default int update(Class<?> view, T entity, Query query) {
        return update(null, view, entity, query);
    }


    default int update(String tableName, Class<?> view, T entity, ID... ids) {
        return update(tableName, view, entity, Arrays.asList(ids));
    }

    default int update(String tableName, Class<?> view, T entity, Collection<ID> ids) {
        return update(tableName, view, entity, null, true, ids, null);
    }

    default int update(String tableName, Class<?> view, T entity, Queryable query) {
        return update(tableName, view, entity, query.convert());
    }

    default int update(String tableName, Class<?> view, T entity, Query query) {
        return update(tableName, view, entity, null, true, null, query);
    }

    default int update(String tableName, Class<?> view, T entity, boolean selective) {
        return update(tableName, view, entity, selective, entity.getId());
    }

    default int update(String tableName, Class<?> view, T entity, boolean selective, ID... ids) {
        return update(tableName, view, entity, selective, Arrays.asList(ids));
    }

    default int update(String tableName, Class<?> view, T entity, boolean selective, Collection<ID> ids) {
        return update(tableName, view, entity, null, selective, ids, null);
    }

    default int update(String tableName, Class<?> view, T entity, boolean selective, Queryable query) {
        return update(tableName, view, entity, selective, query.convert());
    }

    default int update(String tableName, Class<?> view, T entity, boolean selective, Query query) {
        return update(tableName, view, entity, null, selective, null, query);
    }


    default int update(T... entities) {
        return update(Arrays.asList(entities));
    }

    default int update(Collection<T> entities) {
        return entities.stream()
                .mapToInt(this::update)
                .sum();
    }

    default int update(String tableName, T... entities) {
        return update(tableName, Arrays.asList(entities));
    }

    default int update(String tableName, Collection<T> entities) {
        return update(tableName, null, entities);
    }

    default int update(Class<?> view, T... entities) {
        return update(view, Arrays.asList(entities));
    }

    default int update(Class<?> view, Collection<T> entities) {
        return update(null, view, entities);
    }

    default int update(boolean selective, T... entities) {
        return update(Arrays.asList(entities), selective);
    }

    default int update(Collection<T> entities, boolean selective) {
        return entities.stream()
                .mapToInt(it -> update(it, selective))
                .sum();
    }

    default int update(String tableName, Class<?> view, Collection<T> entities) {
        return update(tableName, view, entities, true);
    }

    default int update(String tableName, Collection<T> entities, boolean selective) {
        return update(tableName, null, entities, selective);
    }

    default int update(String tableName, Class<?> view, Collection<T> entities, boolean selective) {
        return entities.stream()
                .mapToInt(it -> update(tableName, view, it, selective))
                .sum();
    }


    // -----------------Update---------

    default int update(Update update, ID... ids) {
        return update(update, Arrays.asList(ids));
    }

    default int update(Update update, Collection<ID> ids) {
        return update(null, null, null, update, false, ids, null);
    }

    default int update(String tableName, Update update, ID... ids) {
        return update(tableName, update, Arrays.asList(ids));
    }

    default int update(String tableName, Update update, Collection<ID> ids) {
        return update(tableName, null, null, update, false, ids, null);
    }

    default int update(Update update, Queryable query) {
        return update(update, query.convert());
    }

    default int update(Update update, Query query) {
        return update(null, update, query);
    }

    default int update(String tableName, Update update, Queryable query) {
        return update(tableName, update, query.convert());
    }

    default int update(String tableName, Update update, Query query) {
        return update(tableName, null, null, update, false, null, query);
    }

    /**
     * 更新数据并返回影响的行数
     *
     * @param tableName 表名
     * @param view      视图
     * @param entity    实体，值不为 {@code null}时，忽略 {@code update} 的值
     * @param update    更新，仅当 {@code entity}为空时有效
     * @param selective 有选择的，值为{@code true}时，不更新值为 {@code null}的属性。
     * @param ids       要更新的IDS
     * @param query     更新条件
     * @return 更新数据后影响的行数
     */
    int update(@Param("tableName") String tableName, @Param("view") Class<?> view,
               @Param("entity") T entity, @Param("update") Update update, @Param("selective") boolean selective,
               @Param("ids") Collection<ID> ids, @Param("query") Query query);

    /*=========================================== DELETE ===========================================*/

    default int delete(T... entities) {
        return delete(Arrays.asList(entities));
    }

    default int delete(String tableName, T... entities) {
        return delete(tableName, Arrays.asList(entities));
    }

    default int delete(List<T> entities) {
        return delete(entities.stream().map(IEntity::getId).collect(Collectors.toList()));
    }

    default int delete(String tableName, List<T> entities) {
        return delete(entities.stream().map(IEntity::getId).collect(Collectors.toList()));
    }

    default int delete(ID... ids) {
        return delete(Arrays.asList(ids));
    }

    default int delete(String tableName, ID... ids) {
        return delete(tableName, Arrays.asList(ids));
    }

    default int delete(Collection<ID> ids) {
        return delete(null, ids);
    }

    default int delete(String tableName, Collection<ID> ids) {
        return delete(tableName, ids, null);
    }

    default int delete(Queryable query) {
        return delete(query.convert());
    }

    default int delete(Query query) {
        return delete(null, null, query);
    }

    default int delete(String tableName, Queryable query) {
        return delete(tableName, query.convert());
    }

    default int delete(String tableName, Query query) {
        return delete(tableName, null, query);
    }

    /**
     * 删除符合条件的数据并返回影响的行数
     *
     * @param tableName 表名
     * @param ids       IDS
     * @param query     条件
     * @return 删除符合条件的数据所影响的行数
     */
    int delete(@Param("tableName") String tableName, @Param("ids") Collection<ID> ids, @Param("query") Query query);

    default <PARAM> void delete(Query query, Listener<PARAM, Integer> listener) {
        this.delete(null, query, listener);
    }

    default <PARAM> void delete(String tableName, Query query, Listener<PARAM, Integer> listener) {
        Long limit = query.getLimit().getLimit();
        Assert.isNull(limit, "limit is null");
        Assert.isNull(listener, "listener is null");
        int offset = 0;
        PARAM param = listener.onInit();
        listener.onStart(param);
        while (true) {
            int rows = delete(tableName, query);
            if (!listener.onListening(offset, param, rows)) break;
            if (rows < limit) break;
        }
        listener.onFinish(param);
    }

    /*=========================================== SELECT ===========================================*/

    default List<T> select() {
        return select((Query) null);
    }

    default List<T> select(String tableName) {
        return select(tableName, (Query) null);
    }

    default List<T> select(Class<?> view) {
        return select(view, (Query) null);
    }

    default List<T> select(String tableName, Class<?> view) {
        return select(tableName, view, (Query) null);
    }

    default List<T> select(ID... ids) {
        return select(Arrays.asList(ids));
    }

    default List<T> select(String tableName, ID... ids) {
        return select(tableName, Arrays.asList(ids));
    }

    default List<T> select(Class<?> view, ID... ids) {
        return select(view, Arrays.asList(ids));
    }

    default List<T> select(String tableName, Class<?> view, ID... ids) {
        return select(tableName, view, Arrays.asList(ids));
    }

    default List<T> select(Collection<ID> ids) {
        return select(null, null, ids);
    }

    default List<T> select(String tableName, Collection<ID> ids) {
        return select(tableName, null, ids, null);
    }

    default List<T> select(Class<?> view, Collection<ID> ids) {
        return select(null, view, ids);
    }

    default List<T> select(String tableName, Class<?> view, Collection<ID> ids) {
        return select(tableName, view, ids, null);
    }

    default List<T> select(Queryable query) {
        return select(query.convert());
    }


    default List<T> select(Query query) {
        return select(null, null, query);
    }

    default List<T> select(String tableName, Queryable query) {
        return select(tableName, query.convert());
    }

    default List<T> select(String tableName, Query query) {
        return select(tableName, null, null, query);
    }

    default List<T> select(Class<?> view, Queryable query) {
        return select(view, query.convert());
    }

    default List<T> select(Class<?> view, Query query) {
        return select(null, view, query);
    }

    default List<T> select(String tableName, Class<?> view, Queryable query) {
        return select(tableName, view, query.convert());
    }

    default List<T> select(String tableName, Class<?> view, Query query) {
        return select(tableName, view, null, query);
    }

    /**
     * 根据 {@link ID} 集合或 {@link Query} 查询
     *
     * @param tableName 表名
     * @param view      视图
     * @param ids       要查询的IDS
     * @param query     查询条件
     */
    List<T> select(@Param("tableName") String tableName, @Param("view") Class<?> view, @Param("ids") Collection<ID> ids, @Param("query") Query query);


    /*=========================================== SELECT ONE ===========================================*/

    default T selectOne(ID id) {
        return selectOne(null, null, id, null);
    }

    default T selectOne(String tableName, ID id) {
        return selectOne(tableName, null, id, null);
    }

    default T selectOne(Class<?> view, ID id) {
        return selectOne(null, view, id, null);
    }

    default T selectOne(String tableName, Class<?> view, ID id) {
        return selectOne(tableName, view, id, null);
    }

    default T selectOne(Queryable query) {
        return selectOne(query.convert());
    }

    default T selectOne(Query query) {
        return selectOne(null, null, query.limit(1));
    }

    default T selectOne(String tableName, Queryable query) {
        return selectOne(tableName, null, query.convert().limit(1));
    }


    default T selectOne(String tableName, Query query) {
        return selectOne(tableName, null, query.limit(1));
    }

    default T selectOne(Class<?> view, Queryable query) {
        return selectOne(view, query.convert().limit(1));
    }

    default T selectOne(Class<?> view, Query query) {
        return selectOne(null, view, null, query.limit(1));
    }

    default T selectOne(String tableName, Class<?> view, Queryable query) {
        return selectOne(tableName, view, query.convert().limit(1));
    }

    default T selectOne(String tableName, Class<?> view, Query query) {
        return selectOne(tableName, view, null, query.limit(1));
    }

    /**
     * 返回符合查询 {@link ID} 或 {@link Query} 的一个结果，当找不到时返回 {@code null}
     *
     * @param tableName 表名
     * @param view      视图
     * @param id        ID
     * @param query     query
     * @return 符合查询 {@link ID} 或 {@link Query} 的一个结果
     */
    T selectOne(@Param("tableName") String tableName, @Param("view") Class<?> view, @Param("id") ID id, @Param("query") Query query);

    /*=========================================== SCANNER ===========================================*/

    default <PARAM> void select(Queryable query, Listener<PARAM, List<T>> listener) {
        select(query.convert(), listener);
    }

    default <PARAM> void select(Query query, Listener<PARAM, List<T>> listener) {
        select(null, null, query, listener);
    }

    default <PARAM> void select(Class<?> view, Queryable query, Listener<PARAM, List<T>> listener) {
        select(view, query.convert(), listener);
    }

    default <PARAM> void select(Class<?> view, Query query, Listener<PARAM, List<T>> listener) {
        select(null, view, query, listener);
    }

    default <PARAM> void select(String tableName, Queryable query, Listener<PARAM, List<T>> listener) {
        select(tableName, query.convert(), listener);
    }

    default <PARAM> void select(String tableName, Query query, Listener<PARAM, List<T>> listener) {
        select(tableName, null, query, listener);
    }

    default <PARAM> void select(@Param("tableName") String tableName, @Param("view") Class<?> view, @Param("query") Queryable query, Listener<PARAM, List<T>> listener) {
        select(tableName, view, query.convert(), listener);
    }

    default <PARAM> void select(String tableName, Class<?> view, Query query, Listener<PARAM, List<T>> listener) {
        if (Assert.isNull(query.getPage()) || Assert.isNull(query.getSize())) {
            throw new IllegalArgumentException("query page or size is null");
        }
        Assert.isNull(listener, "listener is null");
        int index = query.getPage();
        int offset = 0;
        PARAM param = listener.onInit();
        listener.onStart(param);
        while (true) {
            query.page(index + offset);
            List<T> list = select(tableName, view, query);
            offset++;
            if (!listener.onListening(offset, param, list)) break;
            if (Assert.isEmpty(list) || list.size() < query.getSize()) break;
        }
        listener.onFinish(param);
    }

    /*=========================================== SELECT IDS===========================================*/

    default List<ID> selectIds(Queryable query) {
        return selectIds(query.convert());
    }

    default List<ID> selectIds(Query query) {
        return selectIds(null, query);
    }

    default List<ID> selectIds(String tableName, Queryable query) {
        return selectIds(tableName, query.convert());
    }

    List<ID> selectIds(@Param("tableName") String tableName, @Param("query") Query query);


    /*=========================================== SELECT COUNT ===========================================*/

    default long selectCount() {
        return selectCount((Query) null);
    }

    default long selectCount(String tableName) {
        return selectCount(tableName, (Query) null);
    }

    default long selectCount(Queryable query) {
        return selectCount(query.convert());
    }

    default long selectCount(Query query) {
        return selectCount(null, query);
    }

    default long selectCount(String tableName, Queryable query) {
        return selectCount(tableName, query.convert());
    }

    /**
     * 返回符合查询条件 {@link Query}的结果集的大小
     *
     * @param tableName 表名
     * @param query     query
     * @return 符合查询条件 {@link Query}的结果集的大小
     */
    long selectCount(@Param("tableName") String tableName, @Param("query") Query query);

    /*=========================================== IS EXISTS ===========================================*/
    default boolean isExists(ID id) {
        return selectOne(id) != null;
    }

    default boolean isExists(String tableName, ID id) {
        return selectOne(tableName, id) != null;
    }

    default boolean isExists(Queryable query) {
        return isExists(query.convert());
    }

    default boolean isExists(Query query) {
        return selectCount(query) > 0;
    }

    default boolean isExists(String tableName, Queryable query) {
        return isExists(tableName, query.convert());
    }

    default boolean isExists(String tableName, Query query) {
        return selectCount(tableName, query) > 0;
    }


    /*=========================================== TRUNCATE ===========================================*/

    default void truncate() {
        truncate(null);
    }

    /**
     * 清空表数据
     *
     * @param tableName 表名
     */
    void truncate(@Param("tableName") String tableName);


}

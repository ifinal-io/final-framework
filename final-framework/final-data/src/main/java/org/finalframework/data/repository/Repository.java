package org.finalframework.data.repository;

import org.finalframework.annotation.IEntity;
import org.finalframework.annotation.IQuery;
import org.finalframework.annotation.Pageable;
import org.finalframework.data.query.Update;
import org.finalframework.data.trigger.annotation.TriggerPoint;
import org.finalframework.util.Asserts;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The superinterface of {@code CURD}.
 *
 * @author likly
 * @version 1.0
 * @date 2018-10-12 13:27
 * @since 1.0
 */
@SuppressWarnings({"unused", "unchecked"})
public interface Repository<ID extends Serializable, T extends IEntity<ID>> {

    /*=========================================== INSERT ===========================================*/
    default int insert(@NonNull T... entities) {
        return insert(Arrays.asList(entities));
    }

    default int insert(boolean ignore, @NotNull T... entities) {
        return insert(ignore, Arrays.asList(entities));
    }

    default int insert(@NonNull Collection<T> entities) {
        return insert(null, null, false, entities);
    }

    default int insert(boolean ignore, @NotNull Collection<T> entities) {
        return insert(null, null, ignore, entities);
    }

    default int insert(@Nullable String table, @NotNull T... entities) {
        return insert(table, Arrays.asList(entities));
    }

    default int insert(@Nullable String table, boolean ignore, @NonNull T... entities) {
        return insert(table, ignore, Arrays.asList(entities));
    }

    default int insert(@Nullable String table, @NotNull Collection<T> entities) {
        return insert(table, null, false, entities);
    }

    default int insert(@Nullable String table, boolean ignore, @NotNull Collection<T> entities) {
        return insert(table, null, ignore, entities);
    }

    default int insert(@Nullable Class<?> view, @NotNull T... entities) {
        return insert(view, Arrays.asList(entities));
    }

    default int insert(@Nullable Class<?> view, boolean ignore, @NotNull T... entities) {
        return insert(view, ignore, Arrays.asList(entities));
    }

    default int insert(@Nullable Class<?> view, @NotNull Collection<T> entities) {
        return insert(null, view, false, entities);
    }

    default int insert(@Nullable Class<?> view, boolean ignore, @NotNull Collection<T> entities) {
        return insert(null, view, ignore, entities);
    }

    default int insert(@Nullable String table, @Nullable Class<?> view, @NotNull T... entities) {
        return insert(table, view, false, Arrays.asList(entities));
    }

    default int insert(@Nullable String table, @Nullable Class<?> view, boolean ignore, @NotNull T... entities) {
        return insert(table, view, ignore, Arrays.asList(entities));
    }


    /**
     * 批量插入数据并返回影响的行数
     *
     * @param table    表名
     * @param view     视图,
     * @param ignore   是否忽略重复数据,{@literal INSERT IGNORE}
     * @param entities 实体集
     * @return 指插入数据所影响的行数
     */
    @TriggerPoint
    int insert(@Nullable String table, @Nullable Class<?> view, boolean ignore, @NonNull Collection<T> entities);

    /*=========================================== REPLACE ===========================================*/
    default int replace(T... entities) {
        return replace(Arrays.asList(entities));
    }

    default int replace(Collection<T> entities) {
        return replace(null, null, entities);
    }

    default int replace(String table, T... entities) {
        return replace(table, Arrays.asList(entities));
    }

    default int replace(String table, Collection<T> entities) {
        return replace(table, null, entities);
    }

    default int replace(Class<?> view, T... entities) {
        return replace(view, Arrays.asList(entities));
    }

    default int replace(Class<?> view, Collection<T> entities) {
        return replace(null, view, entities);
    }

    default int replace(String table, Class<?> view, T... entities) {
        return replace(table, view, Arrays.asList(entities));
    }

    /**
     * 批量插入数据并返回影响的行数
     *
     * @param table    表名
     * @param view     视图
     * @param entities 实体集
     * @return 指插入数据所影响的行数
     */
    @TriggerPoint
    int replace(String table, Class<?> view, Collection<T> entities);

    /*=========================================== SAVE ===========================================*/
    default int save(T... entities) {
        return save(Arrays.asList(entities));
    }

    default int save(Collection<T> entities) {
        return save(null, null, entities);
    }

    default int save(String table, T... entities) {
        return save(table, Arrays.asList(entities));
    }

    default int save(String table, Collection<T> entities) {
        return save(table, null, entities);
    }

    default int save(Class<?> view, T... entities) {
        return save(view, Arrays.asList(entities));
    }

    default int save(Class<?> view, Collection<T> entities) {
        return save(null, view, entities);
    }

    default int save(String table, Class<?> view, T... entities) {
        return save(table, view, Arrays.asList(entities));
    }

    /**
     * 批量插入数据并返回影响的行数
     *
     * @param table    表名
     * @param view     视图
     * @param entities 实体集
     * @return 指插入数据所影响的行数
     */
//    @TriggerPoint
    int save(String table, Class<?> view, Collection<T> entities);



    /*=========================================== UPDATE ===========================================*/

    default int update(T entity) {
        return update((String) null, entity);
    }

    default int update(String table, T entity) {
        return update(table, null, entity);
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


    default int update(T entity, IQuery query) {
        return update((String) null, entity, query);
    }

    default int update(T entity, boolean selective, ID... ids) {
        return update(entity, selective, Arrays.asList(ids));
    }

    default int update(T entity, boolean selective, Collection<ID> ids) {
        return update(null, null, entity, selective, ids);
    }


    default int update(T entity, boolean selective, IQuery query) {
        return update(null, null, entity, selective, query);
    }

    default int update(String table, Class<?> view, T entity) {
        return update(table, view, entity, true);
    }

    default int update(String table, T entity, boolean selective) {
        return update(table, null, entity, selective);
    }

    default int update(String table, T entity, ID... ids) {
        return update(table, entity, Arrays.asList(ids));
    }

    default int update(String table, T entity, Collection<ID> ids) {
        return update(table, null, entity, true, ids);
    }


    default int update(String table, T entity, IQuery query) {
        return update(table, null, entity, query);
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


    default int update(Class<?> view, T entity, IQuery query) {
        return update(null, view, entity, query);
    }


    default int update(String table, Class<?> view, T entity, ID... ids) {
        return update(table, view, entity, Arrays.asList(ids));
    }

    default int update(String table, Class<?> view, T entity, Collection<ID> ids) {
        return update(table, view, entity, null, true, ids, null);
    }


    default int update(String table, Class<?> view, T entity, IQuery query) {
        return update(table, view, entity, null, true, null, query);
    }

    default int update(String table, Class<?> view, T entity, boolean selective) {
        return update(table, view, entity, selective, entity.getId());
    }

    default int update(String table, Class<?> view, T entity, boolean selective, ID... ids) {
        return update(table, view, entity, selective, Arrays.asList(ids));
    }

    default int update(String table, Class<?> view, T entity, boolean selective, Collection<ID> ids) {
        return update(table, view, entity, null, selective, ids, null);
    }


    default int update(String table, Class<?> view, T entity, boolean selective, IQuery query) {
        return update(table, view, entity, null, selective, null, query);
    }


    default int update(T... entities) {
        return update(Arrays.asList(entities));
    }

    default int update(@NonNull Collection<T> entities) {
        return entities.stream()
                .mapToInt(this::update)
                .sum();
    }

    default int update(String table, T... entities) {
        return update(table, Arrays.asList(entities));
    }

    default int update(String table, Collection<T> entities) {
        return update(table, null, entities);
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

    default int update(@NonNull Collection<T> entities, boolean selective) {
        return entities.stream()
                .mapToInt(it -> update(it, selective))
                .sum();
    }

    default int update(String table, Class<?> view, Collection<T> entities) {
        return update(table, view, entities, true);
    }

    default int update(String table, Collection<T> entities, boolean selective) {
        return update(table, null, entities, selective);
    }

    default int update(String table, Class<?> view, Collection<T> entities, boolean selective) {
        return entities.stream()
                .mapToInt(it -> update(table, view, it, selective))
                .sum();
    }


    // -----------------Update---------

    default int update(Update update, ID... ids) {
        return update(update, Arrays.asList(ids));
    }

    default int update(Update update, Collection<ID> ids) {
        return update(null, null, null, update, false, ids, null);
    }

    default int update(String table, Update update, ID... ids) {
        return update(table, update, Arrays.asList(ids));
    }

    default int update(String table, Update update, Collection<ID> ids) {
        return update(table, null, null, update, false, ids, null);
    }

    default int update(Update update, IQuery query) {
        return update(null, update, query);
    }


    default int update(String table, Update update, IQuery query) {
        return update(table, null, null, update, false, null, query);
    }

    /**
     * 更新数据并返回影响的行数
     *
     * @param table     表名
     * @param view      视图
     * @param entity    实体，值不为 {@code null}时，忽略 {@code update} 的值
     * @param update    更新，仅当 {@code entity}为空时有效
     * @param selective 有选择的，值为{@code true}时，不更新值为 {@code null}的属性。
     * @param ids       要更新的IDS
     * @param query     更新条件
     * @return 更新数据后影响的行数
     */
    @TriggerPoint
    int update(String table, Class<?> view,
               T entity, Update update, boolean selective,
               Collection<ID> ids, IQuery query);

    /*=========================================== DELETE ===========================================*/

    default int delete(T... entities) {
        return delete(Arrays.asList(entities));
    }

    default int delete(String table, T... entities) {
        return delete(table, Arrays.asList(entities));
    }

    default int delete(@NonNull List<T> entities) {
        return delete(entities.stream().map(IEntity::getId).collect(Collectors.toList()));
    }

    default int delete(String table, @NonNull List<T> entities) {
        return delete(table, entities.stream().map(IEntity::getId).collect(Collectors.toList()));
    }

    default int delete(ID... ids) {
        return delete(Arrays.asList(ids));
    }

    default int delete(String table, ID... ids) {
        return delete(table, Arrays.asList(ids));
    }

    default int delete(Collection<ID> ids) {
        return delete(null, ids);
    }

    default int delete(String table, Collection<ID> ids) {
        return delete(table, ids, null);
    }


    default int delete(IQuery query) {
        return delete(null, null, query);
    }


    default int delete(String table, IQuery query) {
        return delete(table, null, query);
    }

    /**
     * 删除符合条件的数据并返回影响的行数
     *
     * @param table 表名
     * @param ids   IDS
     * @param query 条件
     * @return 删除符合条件的数据所影响的行数
     */
    @TriggerPoint
    int delete(String table, Collection<ID> ids, IQuery query);

    default <PARAM> void delete(IQuery query, Listener<PARAM, Integer> listener) {
        this.delete(null, query, listener);
    }

    default <PARAM> void delete(String table, @NonNull IQuery query, Listener<PARAM, Integer> listener) {
        Asserts.isNull(listener, "listener is null");
        int offset = 0;
        PARAM param = listener.onInit();
        listener.onStart(param);
        int rows;
        do {
            rows = delete(table, query);
            if (!listener.onListening(offset, param, rows)) {
                break;
            }
        } while (rows > 0);
        listener.onFinish(param);
    }

    /*=========================================== SELECT ===========================================*/

    default List<T> select() {
        return select((IQuery) null);
    }

    default List<T> select(String table) {
        return select(table, (IQuery) null);
    }

    default List<T> select(Class<?> view) {
        return select(view, (IQuery) null);
    }

    default List<T> select(String table, Class<?> view) {
        return select(table, view, (IQuery) null);
    }

    default List<T> select(ID... ids) {
        return select(Arrays.asList(ids));
    }

    default List<T> select(String table, ID... ids) {
        return select(table, Arrays.asList(ids));
    }

    default List<T> select(Class<?> view, ID... ids) {
        return select(view, Arrays.asList(ids));
    }

    default List<T> select(String table, Class<?> view, ID... ids) {
        return select(table, view, Arrays.asList(ids));
    }

    default List<T> select(Collection<ID> ids) {
        return select(null, null, ids);
    }

    default List<T> select(String table, Collection<ID> ids) {
        return select(table, null, ids, null);
    }

    default List<T> select(Class<?> view, Collection<ID> ids) {
        return select(null, view, ids);
    }

    default List<T> select(String table, Class<?> view, Collection<ID> ids) {
        return select(table, view, ids, null);
    }


    default List<T> select(IQuery query) {
        return select(null, null, query);
    }


    default List<T> select(String table, IQuery query) {
        return select(table, null, null, query);
    }


    default List<T> select(Class<?> view, @Nullable IQuery query) {
        return select(null, view, query);
    }


    default List<T> select(String table, Class<?> view, @Nullable IQuery query) {
        return select(table, view, null, query);
    }

    /**
     * 根据 {@link ID} 集合或 {@link IQuery} 查询
     *
     * @param table 表名
     * @param view  视图
     * @param ids   要查询的IDS
     * @param query 查询条件
     */
    @TriggerPoint
    List<T> select(String table, Class<?> view, Collection<ID> ids, IQuery query);


    /*=========================================== SELECT ONE ===========================================*/

    default T selectOne(ID id) {
        return selectOne(null, null, id, null);
    }

    default T selectOne(String table, ID id) {
        return selectOne(table, null, id, null);
    }

    default T selectOne(Class<?> view, ID id) {
        return selectOne(null, view, id, null);
    }

    default T selectOne(String table, Class<?> view, ID id) {
        return selectOne(table, view, id, null);
    }


    default T selectOne(@NonNull IQuery query) {
        return selectOne(null, null, query);
    }

    default T selectOne(String table, @NonNull IQuery query) {
        return selectOne(table, null, query);
    }

    default T selectOne(Class<?> view, @NonNull IQuery query) {
        return selectOne(null, view, null, query);
    }

    default T selectOne(String table, Class<?> view, @NonNull IQuery query) {
        return selectOne(table, view, null, query);
    }

    /**
     * 返回符合查询 {@link ID} 或 {@link IQuery} 的一个结果，当找不到时返回 {@code null}
     *
     * @param table 表名
     * @param view  视图
     * @param id    ID
     * @param query query
     * @return 符合查询 {@link ID} 或 {@link IQuery} 的一个结果
     */
    @TriggerPoint
    T selectOne(String table, Class<?> view, ID id, IQuery query);

    /*=========================================== SCANNER ===========================================*/


    default <PARAM> void scan(Pageable query, Listener<PARAM, List<T>> listener) {
        scan(null, null, query, listener);
    }


    default <PARAM> void scan(Class<?> view, @NonNull Pageable query, Listener<PARAM, List<T>> listener) {
        scan(null, view, query, listener);
    }


    default <PARAM> void scan(String table, Pageable query, Listener<PARAM, List<T>> listener) {
        scan(table, null, query, listener);
    }

    default <PARAM> void scan(String table, Class<?> view, @NonNull Pageable query, Listener<PARAM, List<T>> listener) {
        if (Asserts.isNull(query.getPage()) || Asserts.isNull(query.getSize())) {
            throw new IllegalArgumentException("query page or size is null");
        }
        Asserts.isNull(listener, "listener is null");
        int index = query.getPage();
        int offset = 0;
        PARAM param = listener.onInit();
        listener.onStart(param);
        while (true) {
            query.setPage(index + offset);
            List<T> list = select(table, view, query);
            offset++;
            if (!listener.onListening(offset, param, list)) {
                break;
            }
            if (Asserts.isEmpty(list) || list.size() < query.getSize()) {
                break;
            }
        }
        listener.onFinish(param);
    }

    /*=========================================== SELECT IDS===========================================*/

    default List<ID> selectIds(IQuery query) {
        return selectIds(null, query);
    }

    /**
     * 返回符合查询条件 {@link IQuery} 的主键集合 {@link ID}
     *
     * @param table 表名
     * @param query query
     * @return 符合查询条件 {@link IQuery} 的主键集合 {@link ID}
     */
    List<ID> selectIds(String table, IQuery query);


    /*=========================================== SELECT COUNT ===========================================*/

    default long selectCount() {
        return selectCount((String) null);
    }

    default long selectCount(@Nullable String table) {
        return selectCount(table, null, null);
    }


    default long selectCount(@Nullable IQuery query) {
        return selectCount(null, null, query);
    }


    default long selectCount(String table, IQuery query) {
        return selectCount(table, null, query);
    }

    default long selectCount(ID... ids) {
        return selectCount(Arrays.asList(ids));
    }

    default long selectCount(Collection<ID> ids) {
        return selectCount(null, ids);
    }

    default long selectCount(String table, ID... ids) {
        return selectCount(table, Arrays.asList(ids));
    }

    default long selectCount(String table, Collection<ID> ids) {
        return selectCount(table, ids, null);
    }


    /**
     * 返回符合查询条件 {@link IQuery}的结果集的大小
     *
     * @param table 表名
     * @param query query
     * @return 符合查询条件 {@link IQuery}的结果集的大小
     */
    long selectCount(@Nullable String table, @Nullable Collection<ID> ids, @Nullable IQuery query);

    /*=========================================== IS EXISTS ===========================================*/
    default boolean isExists(ID id) {
        return selectOne(id) != null;
    }

    default boolean isExists(String table, ID id) {
        return selectOne(table, id) != null;
    }


    default boolean isExists(IQuery query) {
        return selectCount(query) > 0;
    }

    default boolean isExists(String table, IQuery query) {
        return selectCount(table, null, query) > 0;
    }


    /*=========================================== TRUNCATE ===========================================*/

    default void truncate() {
        truncate(null);
    }

    /**
     * 清空表数据
     *
     * @param table 表名
     */
    void truncate(String table);

}

package org.ifinal.finalframework.mybatis.mapper;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import org.ifinal.finalframework.annotation.core.IEntity;
import org.ifinal.finalframework.annotation.core.IQuery;
import org.ifinal.finalframework.data.repository.Repository;
import org.ifinal.finalframework.mybatis.sql.provider.DeleteSqlProvider;
import org.ifinal.finalframework.mybatis.sql.provider.InsertSqlProvider;
import org.ifinal.finalframework.mybatis.sql.provider.SelectCountSqlProvider;
import org.ifinal.finalframework.mybatis.sql.provider.SelectIdsSqlProvider;
import org.ifinal.finalframework.mybatis.sql.provider.SelectSqlProvider;
import org.ifinal.finalframework.mybatis.sql.provider.TruncateSqlProvider;
import org.ifinal.finalframework.mybatis.sql.provider.UpdateSqlProvider;
import org.ifinal.finalframework.query.Update;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.builder.annotation.ProviderContext;

/**
 * @author likly
 * @version 1.0.0
 * @see InsertSqlProvider
 * @see UpdateSqlProvider
 * @see SelectSqlProvider
 * @see SelectIdsSqlProvider
 * @see SelectCountSqlProvider
 * @see DeleteSqlProvider
 * @since 1.0.0
 */
@SuppressWarnings("all")
public interface AbsMapper<I extends Serializable, T extends IEntity<I>> extends Repository<I, T> {

    /**
     * @param table    表名
     * @param view     视图,
     * @param ignore   是否忽略重复数据,{@literal INSERT IGNORE}
     * @param entities 实体集
     * @see InsertSqlProvider#insert(ProviderContext, Map)
     */
    @Override
    @Options(useGeneratedKeys = true, keyProperty = "list.id", keyColumn = "id")
    @InsertProvider(InsertSqlProvider.class)
    int insert(@Nullable @Param("table") String table, @Nullable @Param("view") Class<?> view,
        @Param("ignore") boolean ignore, @NonNull @Param("list") Collection<T> entities);

    /**
     * @param table    表名
     * @param view     视图
     * @param entities 实体集
     * @see InsertSqlProvider#replace(ProviderContext, Map)
     */
    @Override
    @Options(useGeneratedKeys = true, keyProperty = "list.id", keyColumn = "id")
    @InsertProvider(InsertSqlProvider.class)
    int replace(@Nullable @Param("table") String table, @Nullable @Param("view") Class<?> view,
        @NonNull @Param("list") Collection<T> entities);

    /**
     * @param table    表名
     * @param view     视图
     * @param entities 实体集
     * @see InsertSqlProvider#save(ProviderContext, Map)
     */
    @Override
    @Options(useGeneratedKeys = true, keyProperty = "list.id", keyColumn = "id")
    @InsertProvider(InsertSqlProvider.class)
    int save(@Nullable @Param("table") String table, @Nullable @Param("view") Class<?> view,
        @NonNull @Param("list") Collection<T> entities);

    @Override
    @UpdateProvider(UpdateSqlProvider.class)
    int update(@Param("table") String table, @Param("view") Class<?> view,
        @Param("entity") T entity, @Param("update") Update update, @Param("selective") boolean selective,
        @Param("ids") Collection<I> ids, @Param("query") IQuery query);

    @Override
    @DeleteProvider(DeleteSqlProvider.class)
    int delete(@Nullable @Param("table") String table, @Nullable @Param("ids") Collection<I> ids,
        @Nullable @Param("query") IQuery query);

    @Override
    @SelectProvider(SelectSqlProvider.class)
    List<T> select(@Param("table") String table, @Param("view") Class<?> view, @Param("ids") Collection<I> ids,
        @Param("query") IQuery query);

    @Override
    @SelectProvider(SelectSqlProvider.class)
    T selectOne(@Param("table") String table, @Param("view") Class<?> view, @Param("id") I id,
        @Param("query") IQuery query);

    @Override
    @SelectProvider(SelectIdsSqlProvider.class)
    List<I> selectIds(@Nullable @Param("table") String table, @NonNull @Param("query") IQuery query);

    @Override
    @SelectProvider(SelectCountSqlProvider.class)
    long selectCount(@Param("table") String table, @Param("ids") Collection<I> ids, @Param("query") IQuery query);

    @Override
    @UpdateProvider(TruncateSqlProvider.class)
    void truncate(@Nullable @Param("table") String table);

}

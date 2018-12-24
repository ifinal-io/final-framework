package cn.com.likly.finalframework.mybatis.mapper;

import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.domain.Update;
import cn.com.likly.finalframework.data.entity.IEntity;
import cn.com.likly.finalframework.data.mapping.Entity;
import cn.com.likly.finalframework.data.repository.RepositoryTemplate;
import cn.com.likly.finalframework.mybatis.provider.MapperProvider;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 17:49
 * @since 1.0
 */
@SuppressWarnings("all")
//@Mapper
public interface DefaultMapper<ID extends Serializable, T extends IEntity<ID>> extends RepositoryTemplate<ID, T> {

    /*=========================================================== INSERT ===========================================================*/

    @Override
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @InsertProvider(type = MapperProvider.class, method = "insert")
    int insert(@Param("holder") Entity<T> holder, @Param("list") Collection<T> entities);


    @Override
    @SelectKey(statement = "SELECT REPLACE(UUID(), '-', '') FROM dual", resultType = String.class, keyColumn = "id", keyProperty = "id", before = true)
    @InsertProvider(type = MapperProvider.class, method = "insert")
    int insertUuid(@Param("holder") Entity<T> holder, @Param("list") Collection<T> entities);


    @Override
    @SelectKey(statement = "SELECT MD5(REPLACE(UUID(), '-', '')) FROM dual", resultType = String.class, keyColumn = "id", keyProperty = "id", before = true)
    @InsertProvider(type = MapperProvider.class, method = "insert")
    int insertMd5(@Param("holder") Entity<T> holder, @Param("list") Collection<T> entities);


    @Override
    @InsertProvider(type = MapperProvider.class, method = "insert")
    int insertOther(@Param("holder") Entity<T> holder, @Param("list") Collection<T> entities);


    /*=========================================================== SET ===========================================================*/

    @Override
    @UpdateProvider(type = MapperProvider.class, method = "update")
    int update(@Param("holder") Entity<T> holder, @Param("entity") T entity, @Param("update") Update update, @Param("query") Query query);


    /*=========================================================== DELETE ===========================================================*/

    @Override
    @DeleteProvider(type = MapperProvider.class, method = "delete")
    int delete(@Param("holder") Entity<T> holder, @Param("query") Query query);


    /*=========================================================== SELECT ===========================================================*/

    @Override
    @SelectProvider(type = MapperProvider.class, method = "select")
    List<T> select(@Param("holder") Entity<T> holder, @Param("query") Query query);

    @Override
    @SelectProvider(type = MapperProvider.class, method = "select")
    T selectOne(@Param("holder") Entity<T> holder, @Param("query") Query query);

    @Override
//    @SelectProvider(type = MapperProvider.class, method = "selectCount")
    long selectCount(@Param("holder") Entity<T> holder, @Param("query") Query query);

}

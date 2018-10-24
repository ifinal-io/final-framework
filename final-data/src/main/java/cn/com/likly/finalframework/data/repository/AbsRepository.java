package cn.com.likly.finalframework.data.repository;

import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.domain.Update;
import cn.com.likly.finalframework.data.entity.Entity;
import cn.com.likly.finalframework.data.mapping.holder.EntityHolder;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author likly
 * @version 1.0
 * @date 2018-10-22 22:34
 * @since 1.0
 */
public abstract class AbsRepository<ID extends Serializable, T extends Entity<ID>> implements Repository<ID, T> {

    @Override
    public int insert(T... entities) {
        return getRepositoryTemplate().insert(getEntityHolder(), entities);
    }

    @Override
    public int insert(Collection<T> entities) {
        return getRepositoryTemplate().insert(getEntityHolder(), entities);
    }

    @Override
    public int update(T... entities) {
        return Arrays.stream(entities)
                .mapToInt(it -> getRepositoryTemplate().update(getEntityHolder(), it))
                .sum();
    }

    @Override
    public int update(Collection<T> entities) {
        return entities.stream()
                .mapToInt(it -> getRepositoryTemplate().update(getEntityHolder(), it))
                .sum();
    }

    @Override
    public int update(Update update, ID... ids) {
        return getRepositoryTemplate().update(getEntityHolder(), update, ids);
    }

    @Override
    public int update(Update update, Collection<ID> ids) {
        return getRepositoryTemplate().update(getEntityHolder(), update, ids);
    }

    @Override
    public int update(Update update, Query query) {
        return getRepositoryTemplate().update(getEntityHolder(), update, query);
    }

    @Override
    public int delete(T... entities) {
        return getRepositoryTemplate().delete(getEntityHolder(), entities);
    }

    @Override
    public int delete(List<T> entities) {
        return getRepositoryTemplate().delete(getEntityHolder(), entities);
    }

    @Override
    public int delete(ID... ids) {
        return getRepositoryTemplate().delete(getEntityHolder(), ids);
    }

    @Override
    public int delete(Collection<ID> ids) {
        return getRepositoryTemplate().delete(getEntityHolder(), ids);
    }

    @Override
    public int delete(Query query) {
        return getRepositoryTemplate().delete(getEntityHolder(), query);
    }

    @Override
    public List<T> select(ID... ids) {
        return getRepositoryTemplate().select(getEntityHolder(), ids);
    }

    @Override
    public List<T> select(Collection<ID> ids) {
        return getRepositoryTemplate().select(getEntityHolder(), ids);
    }

    @Override
    public List<T> select(Query query) {
        return getRepositoryTemplate().select(getEntityHolder(), query);
    }

    @Override
    public T selectOne(ID id) {
        return getRepositoryTemplate().selectOne(getEntityHolder(), id);
    }

    @Override
    public T selectOne(Query query) {
        return getRepositoryTemplate().selectOne(getEntityHolder(), query);
    }

    @Override
    public long selectCount(Query query) {
        return getRepositoryTemplate().selectCount(getEntityHolder(), query);
    }

    @Override
    public long selectCount() {
        return getRepositoryTemplate().selectCount(getEntityHolder());
    }

    @Override
    public boolean isExists(ID id) {
        return getRepositoryTemplate().isExists(getEntityHolder(), id);
    }

    @Override
    public boolean isExists(Query query) {
        return getRepositoryTemplate().isExists(getEntityHolder(), query);
    }

    protected abstract EntityHolder<T> getEntityHolder();

    protected abstract RepositoryTemplate<ID, T> getRepositoryTemplate();
}

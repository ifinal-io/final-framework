package cn.com.likly.finalframework.data.mybatis.mapper;

import cn.com.likly.finalframework.data.entity.IEntity;
import cn.com.likly.finalframework.data.repository.Repository;

import java.io.Serializable;

/**
 * @author likly
 * @version 1.0
 * @date 2018-09-27 17:49
 * @since 1.0
 */
@SuppressWarnings("all")
public interface AbsMapper<ID extends Serializable, T extends IEntity<ID>> extends Repository<ID, T> {


}

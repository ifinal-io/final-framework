package com.ilikly.finalframework.mybatis.mapper;

import com.ilikly.finalframework.data.entity.IEntity;
import com.ilikly.finalframework.data.repository.Repository;

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

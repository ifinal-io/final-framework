package cn.com.likly.finalframework.data.dao.mapper;

import cn.com.likly.finalframework.data.domain.Query;
import cn.com.likly.finalframework.data.domain.Update;
import cn.com.likly.finalframework.data.entity.IEntity;
import cn.com.likly.finalframework.data.mybatis.mapper.AbsMapper;
import org.apache.ibatis.annotations.*;


import cn.com.likly.finalframework.data.entity.Person;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.annotation.Generated;

@Mapper
@Generated("cn.com.likly.finalframework.coding.EntityProcessor")
public interface PersonMapper extends AbsMapper<Long, Person> {


}
